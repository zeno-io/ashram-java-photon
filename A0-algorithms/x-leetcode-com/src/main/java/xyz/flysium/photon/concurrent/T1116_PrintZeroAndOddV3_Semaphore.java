package xyz.flysium.photon.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * <p>
 * https://leetcode-cn.com/problems/print-zero-even-odd/
 *
 * @author zeno
 */
public class T1116_PrintZeroAndOddV3_Semaphore {

  public static final int TIMES = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumerOneTime(false, () -> new ZeroEvenOdd(TIMES), (inst) -> {
      inst.zero(System.out::print);
    }, (inst) -> {
      inst.even(System.out::print);
    }, (inst) -> {
      inst.odd(System.out::print);
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class ZeroEvenOdd {

    private final int n;
    // zero first
    private final Semaphore zero = new Semaphore(1);
    private final Semaphore odd = new Semaphore(0);
    private final Semaphore even = new Semaphore(0);
    private final AtomicInteger num = new AtomicInteger();

    public ZeroEvenOdd(int n) {
      this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        zero.acquire(1);
        printNumber.accept(0);
        int numInt = num.incrementAndGet();
        // 奇数
        if ((numInt & (~numInt + 1)) == 1) {
          odd.release(1);
        } else {
          even.release(1);
        }
      }
      odd.release(1);
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < (n >> 1); i++) {
        even.acquire(1);
        printNumber.accept(num.get());
        zero.release(1);
      }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < ((n + 1) >> 1); i++) {
        odd.acquire(1);
        printNumber.accept(num.get());
        zero.release(1);
      }
    }
  }

}

