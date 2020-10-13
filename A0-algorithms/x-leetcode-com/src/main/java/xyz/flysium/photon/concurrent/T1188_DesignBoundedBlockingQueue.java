package xyz.flysium.photon.concurrent;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1188. 设计有限阻塞队列
 * <p>
 * https://leetcode-cn.com/problems/design-bounded-blocking-queue
 *
 * @author zeno
 */
public class T1188_DesignBoundedBlockingQueue {

  static final int PRODUCER_THREAD_SIZE = 4;
  static final int CONSUMER_THREAD_SIZE = 3;
  static final BlockingQueue<Integer> DATA = new LinkedBlockingQueue() {
    {
      this.offer(1);
      this.offer(0);
      this.offer(2);
      this.offer(3);
    }
  };

  public static void main(String[] args) throws InterruptedException {
    BoundedBlockingQueue q = new BoundedBlockingQueue(PRODUCER_THREAD_SIZE);
    final StringBuffer buf = new StringBuffer("[");
    long l = TestSupport.testWithMyBiConsumer(1, false, () -> q, buf,
      TestSupport.combine((inst, buffer) -> {
        while (!DATA.isEmpty()) {
          Integer e = DATA.poll();
          inst.enqueue(e);
//          System.out.println("enqueue: " + e);
        }
      }, PRODUCER_THREAD_SIZE, (inst, buffer) -> {
        String str = inst.dequeue() + ",";
        buffer.append(str);
      }, CONSUMER_THREAD_SIZE));
    buf.append(q.size());
    buf.append("]");
    System.out.println(buf.toString());
    System.out.println(TestSupport.toMillisString(l));
  }

  static class BoundedBlockingQueue {

    private final int capacity;
    private final LinkedList<Integer> l = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
      this.capacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
      lock.lock();
      try {
        while (l.size() >= capacity) {
          notFull.await();
        }
        l.addLast(element);
        notEmpty.signalAll();
      } finally {
        lock.unlock();
      }
    }

    public int dequeue() throws InterruptedException {
      lock.lock();
      try {
        while (l.size() <= 0) {
          notEmpty.await();
        }
        int e = l.removeFirst();
        notFull.signalAll();
        return e;
      } finally {
        lock.unlock();
      }
    }

    public int size() {
      return l.size();
    }

  }

}
