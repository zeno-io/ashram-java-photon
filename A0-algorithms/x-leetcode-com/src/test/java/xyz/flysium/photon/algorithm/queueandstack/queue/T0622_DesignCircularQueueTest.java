package xyz.flysium.photon.algorithm.queueandstack.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.algorithm.queueandstack.queue.basic.T0622_DesignCircularQueue;
import xyz.flysium.photon.algorithm.queueandstack.queue.basic.T0622_DesignCircularQueue_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0622_DesignCircularQueueTest {

  private final static int SIZE = 10;

  @Test
  public void test() {
    int times = 10000;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int time = 0; time < times; time++) {
      T0622_DesignCircularQueue.MyCircularQueue q =
        new T0622_DesignCircularQueue.MyCircularQueue(SIZE);
      Deque<Integer> jdkQueue = new ArrayDeque<>(SIZE);
      for (int i = 0; i < 100; i++) {
        if (jdkQueue.isEmpty()) {
          int e = random.nextInt(100);
          jdkQueue.addLast(e);
          q.enQueue(e);
        } else if (jdkQueue.size() == SIZE) {
          Integer e = jdkQueue.removeFirst();
          Assert.assertEquals(e.intValue(), q.Front());
          q.deQueue();
        } else {
          Assert.assertEquals(jdkQueue.peekLast().intValue(), q.Rear());

          int possibility = random.nextInt(100);
          if (possibility < 50) {
            int e = random.nextInt(100);
            jdkQueue.addLast(e);
            q.enQueue(e);
          } else {
            Integer e = jdkQueue.removeFirst();
            Assert.assertEquals(e.intValue(), q.Front());
            q.deQueue();
          }
        }
      }
    }
  }

  @Test
  public void test1() {
    int times = 10000;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int time = 0; time < times; time++) {
      T0622_DesignCircularQueue_1.MyCircularQueue q =
        new T0622_DesignCircularQueue_1.MyCircularQueue(SIZE);
      Deque<Integer> jdkQueue = new ArrayDeque<>(SIZE);
      for (int i = 0; i < 100; i++) {
        if (jdkQueue.isEmpty()) {
          int e = random.nextInt(100);
          jdkQueue.addLast(e);
          q.enQueue(e);
        } else if (jdkQueue.size() == SIZE) {
          Integer e = jdkQueue.removeFirst();
          Assert.assertEquals(e.intValue(), q.Front());
          q.deQueue();
        } else {
          Assert.assertEquals(jdkQueue.peekLast().intValue(), q.Rear());

          int possibility = random.nextInt(100);
          if (possibility < 50) {
            int e = random.nextInt(100);
            jdkQueue.addLast(e);
            q.enQueue(e);
          } else {
            Integer e = jdkQueue.removeFirst();
            Assert.assertEquals(e.intValue(), q.Front());
            q.deQueue();
          }
        }
      }
    }
  }

}
