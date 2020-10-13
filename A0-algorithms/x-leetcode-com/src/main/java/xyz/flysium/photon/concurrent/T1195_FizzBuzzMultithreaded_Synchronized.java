package xyz.flysium.photon.concurrent;

import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * <p>
 * https://leetcode-cn.com/problems/fizz-buzz-multithreaded/
 *
 * @author zeno
 */
public class T1195_FizzBuzzMultithreaded_Synchronized {

  private static final int MAX = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumerOneTime(false, () -> new FizzBuzz(MAX), (inst) -> {
      inst.fizz(() -> System.out.print("fizz,"));
    }, (inst) -> {
      inst.buzz(() -> System.out.print("buzz,"));
    }, (inst) -> {
      inst.fizzbuzz(() -> System.out.print("fizzbuzz,"));
    }, (inst) -> {
      inst.number((x) -> System.out.print(x + ","));
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class FizzBuzz {

    private final int n;
    private int num = 1;

    public FizzBuzz(int n) {
      this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
      while (num <= n) {
        synchronized (this) {
          if (num <= n && (num % 3 == 0 && num % 5 != 0)) {
            printFizz.run();
            num++;
          }
        }
      }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
      while (num <= n) {
        synchronized (this) {
          if (num <= n && (num % 3 != 0 && num % 5 == 0)) {
            printBuzz.run();
            num++;
          }
        }
      }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
      while (num <= n) {
        synchronized (this) {
          if (num <= n && (num % 3 == 0 && num % 5 == 0)) {
            printFizzBuzz.run();
            num++;
          }
        }
      }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
      while (num <= n) {
        synchronized (this) {
          if (num <= n && (num % 3 != 0 && num % 5 != 0)) {
            printNumber.accept(num);
            num++;
          }
        }
      }
    }

  }

}

