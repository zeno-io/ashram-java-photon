package xyz.flysium.photon.concurrent.medium;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1116. 打印零与奇偶数
 * <p>
 * https://leetcode-cn.com/problems/print-zero-even-odd/
 *
 * @author zeno
 */
public class T1116_PrintZeroAndOdd_CAS {

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
    private volatile long a1, a2, a3, a4, a5, a6, a7;
    // 1 -> odd, 2 -> even
    // 10 or 20 -> zero, 10 -> 1, 20 -> 2
    private volatile long flag = 10;
    private volatile long b1, b2, b3, b4, b5, b6, b7;
    private final AtomicInteger num = new AtomicInteger();

    public ZeroEvenOdd(int n) {
      this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        while (flag != 10 && flag != 20) {
          //TimeUnit.NANOSECONDS.sleep(1);
          Thread.yield();
        }
        printNumber.accept(0);
        num.incrementAndGet();
        if (flag == 10) {
          flag = 1;
        } else {
          flag = 2;
        }
      }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < (n >> 1); i++) {
        while (flag != 2) {
          //TimeUnit.NANOSECONDS.sleep(1);
          Thread.yield();
        }
        printNumber.accept(num.get());
        flag = 10;
      }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < ((n + 1) >> 1); i++) {
        while (flag != 1) {
          //TimeUnit.NANOSECONDS.sleep(1);
          Thread.yield();
        }
        printNumber.accept(num.get());
        flag = 20;
      }
    }
  }

}

