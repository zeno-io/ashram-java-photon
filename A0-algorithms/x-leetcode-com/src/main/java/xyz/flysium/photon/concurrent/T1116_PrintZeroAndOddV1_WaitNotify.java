package xyz.flysium.photon.concurrent;

import java.util.function.IntConsumer;

/**
 * 1116. 打印零与奇偶数
 * <p>
 * https://leetcode-cn.com/problems/print-zero-even-odd/
 *
 * @author zeno
 */
public class T1116_PrintZeroAndOddV1_WaitNotify {


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
    // 1 -> odd, 2 -> even
    // 10 or 20 -> zero, 10 -> 1, 20 -> 2
    private int flag = 10;
    private int num = 0;

    public ZeroEvenOdd(int n) {
      this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < n; i++) {
        synchronized (this) {
          while (flag != 10 && flag != 20) {
            wait();
          }
          printNumber.accept(0);
          num++;
          if (flag == 10) {
            flag = 1;
          } else {
            flag = 2;
          }
          notifyAll();
        }
      }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < (n >> 1); i++) {
        synchronized (this) {
          while (flag != 2) {
            wait();
          }
          printNumber.accept(num);
          flag = 10;
          notifyAll();
        }
      }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
      for (int i = 0; i < ((n + 1) >> 1); i++) {
        synchronized (this) {
          while (flag != 1) {
            wait();
          }
          printNumber.accept(num);
          flag = 20;
          notifyAll();
        }
      }
    }
  }

}

