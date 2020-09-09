/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c002_nio.s07_selector_group;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.flysium.photon.c002_nio.s07_selector_group.task.NIOTask;

/**
 * NIO Event Loop
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class NIOEventLoop implements Executor {

  protected static final Logger logger = LoggerFactory.getLogger(NIOEventLoop.class);

  private Selector selector;

  private final Executor executor;
  private final BlockingQueue<Runnable> taskQueue;

  private Thread thread;
  private static final int ST_NOT_STARTED = 1;
  private static final int ST_STARTED = 2;
  private static final int ST_SHUTTING_DOWN = 3;
  private static final int ST_SHUTDOWN = 4;
  private static final int ST_TERMINATED = 5;
  private volatile int state = ST_NOT_STARTED;
  private static final AtomicIntegerFieldUpdater<NIOEventLoop> STATE_UPDATER = AtomicIntegerFieldUpdater
      .newUpdater(NIOEventLoop.class, "state");

  private volatile int ioRatio = 50;

  public NIOEventLoop(Executor executor, BlockingQueue<Runnable> taskQueue) {
    this.executor = executor;
    this.taskQueue = taskQueue;
    try {
      // 创建Selector选择器
      this.selector = Selector.open();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public int getIoRatio() {
    return ioRatio;
  }

  public void setIoRatio(int ioRatio) {
    if (ioRatio <= 0 || ioRatio > 100) {
      throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
    }
    this.ioRatio = ioRatio;
  }

  public Selector unwrappedSelector() {
    return selector;
  }

  @Override
  public void execute(Runnable command) {
    addTask(command);

    boolean inEventLoop = inEventLoop(Thread.currentThread());
    if (!inEventLoop) {
      startThread();
    }

    wakeup(inEventLoop);
  }

  private void wakeup(boolean inEventLoop) {
    if (!inEventLoop) {
      selector.wakeup();
    }
  }

  private void startThread() {
    if (state == ST_NOT_STARTED) {
      if (STATE_UPDATER.compareAndSet(this, ST_NOT_STARTED, ST_STARTED)) {
        boolean success = false;
        try {
          doStartThread();
          success = true;
        } finally {
          if (!success) {
            STATE_UPDATER.compareAndSet(this, ST_STARTED, ST_NOT_STARTED);
          }
        }
      }
    }
  }

  private void doStartThread() {
    executor.execute(new Runnable() {
      @Override
      public void run() {
        thread = Thread.currentThread();
        try {
          //         NIOEventLoop.this.run0(); // simple version.
          NIOEventLoop.this.run();
        } finally {
          STATE_UPDATER.set(NIOEventLoop.this, ST_TERMINATED);
        }
      }
    });
  }

  private void addTask(Runnable task) {
    if (!offerTask(task)) {
      reject(task);
    }
  }

  private boolean offerTask(Runnable task) {
    if (isShutdown()) {
      reject();
    }
    return taskQueue.offer(task);
  }

  protected static void reject() {
    throw new RejectedExecutionException("event executor terminated");
  }

  private void reject(Runnable task) {
    // TODO
    throw new RejectedExecutionException("Task " + task.toString() +
        " rejected from " + this.toString());
  }

  public boolean isShutdown() {
    return state >= ST_SHUTDOWN;
  }

  public boolean inEventLoop(Thread thread) {
    return thread == this.thread;
  }

  /**
   * simple version as {@link #run}
   */
  private void run0() {
    for (; ; ) {
      try {
        int select = selector.select();
        if (select > 0) {
          processSelectionKeys();
        }
        // Ensure we always run tasks.
        runAllTasks();
      } catch (IOException | InterruptedException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private void run() {
    for (; ; ) {
      try {
        // 1. select
        int strategy = 0;
        try {
          strategy = hasTasks() ? selector.selectNow() : selector.select();
        } catch (Exception e) {
          logger.warn(e.getMessage(), e);
        }
        // 2. handle selection keys， 3. run tasks
        final int ioRatio = this.ioRatio;
        if (ioRatio == 100) {
          try {
            if (strategy > 0) {
              processSelectionKeys();
            }
          } finally {
            // Ensure we always run tasks.
            runAllTasks();
          }
        } else if (strategy > 0) {
          long ioStartTime = System.nanoTime();
          try {
            processSelectionKeys();
          } finally {
            // Ensure we always run tasks.
            final long ioTime = System.nanoTime() - ioStartTime;
            runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
          }
        } else {
          // This will run the minimum number of tasks
          runAllTasks(0);
        }
      } catch (IOException | InterruptedException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  private void processSelectionKeys() throws IOException {
    processSelectedKeysPlain(selector.selectedKeys());
  }

  private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
    if (selectedKeys.isEmpty()) {
      return;
    }
    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
    while (keyIterator.hasNext()) {
      SelectionKey key = keyIterator.next();
      keyIterator.remove();

      if (!key.isValid()) {
        continue;
      }
      Object attachment = key.attachment();

      if (attachment instanceof NIOTask) {
        NIOTask task = (NIOTask) attachment;

        safeExecute(task);
      }
    }
  }

  // reference time
  private static final long START_TIME = System.nanoTime();

  private static long nanoTime() {
    return System.nanoTime() - START_TIME;
  }

  protected boolean hasTasks() {
    return !taskQueue.isEmpty();
  }

  /**
   * @see io.netty.channel.nio.NioEventLoop
   */
  private void runAllTasks() throws InterruptedException, IOException {
    for (; ; ) {
      Runnable task = taskQueue.poll();
      if (task == null) {
        break;
      }

      safeExecute(task);
    }
  }

  /**
   * @see io.netty.channel.nio.NioEventLoop
   */
  private void runAllTasks(long timeoutNanos) throws InterruptedException, IOException {
    Runnable task = taskQueue.poll();
    if (task == null) {
      return;
    }
    final long deadline = timeoutNanos > 0 ? (nanoTime() + timeoutNanos) : 0;
    long runTasks = 0;
    long lastExecutionTime;
    for (; ; ) {
      safeExecute(task);

      runTasks++;

      // Check timeout every 64 tasks because nanoTime() is relatively expensive.
      // XXX: Hard-coded value - will make it configurable if it is really a problem.
      if ((runTasks & 0x3F) == 0) {
        lastExecutionTime = nanoTime();
        if (lastExecutionTime >= deadline) {
          break;
        }
      }

      task = taskQueue.poll();
      if (task == null) {
        break;
      }
    }
  }

  protected static void safeExecute(NIOTask task) {
    try {
      task.run();
    } catch (Throwable t) {
      logger.warn("A task raised an exception. Task: {}", task, t);
    }
  }

  protected static void safeExecute(Runnable task) {
    try {
      task.run();
    } catch (Throwable t) {
      logger.warn("A task raised an exception. Task: {}", task, t);
    }
  }

}




