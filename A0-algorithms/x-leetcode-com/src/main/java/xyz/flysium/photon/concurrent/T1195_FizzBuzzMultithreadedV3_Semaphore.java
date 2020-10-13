package xyz.flysium.photon.concurrent;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * 1195. 交替打印字符串
 * <p>
 * https://leetcode-cn.com/problems/fizz-buzz-multithreaded/
 *
 * @author zeno
 */
public class T1195_FizzBuzzMultithreadedV3_Semaphore {

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
    private final Semaphore fizz = new Semaphore(0);
    private final Semaphore buzz = new Semaphore(0);
    private final Semaphore fizzbuzz = new Semaphore(0);
    private final Semaphore numPrint = new Semaphore(0);
    private volatile int num = 1;

    public FizzBuzz(int n) {
      this.n = n;
      this.numPrint.release(1);
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
      if (!isFizz(num % 3, num % 5)) {
        fizz.acquire(1);
      }
      while (num <= n) {
        printFizz.run();
        numPrint.release(1);
        fizz.acquire();
      }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
      if (!isBuzz(num % 3, num % 5)) {
        buzz.acquire(1);
      }
      while (num <= n) {
        printBuzz.run();
        numPrint.release(1);
        buzz.acquire();
      }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
      if (!isFizzBuzz(num % 3, num % 5)) {
        fizzbuzz.acquire(1);
      }
      while (num <= n) {
        printFizzBuzz.run();
        numPrint.release(1);
        fizzbuzz.acquire();
      }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
      while (num <= n) {
        int numInt = num;
        int mod3 = numInt % 3;
        int mod5 = numInt % 5;
        // 统一由 num 线程调度唤醒其他线程，其他线程预先 await，等待唤醒
        if (isFizz(mod3, mod5)) {
          fizz.release(1);
          numPrint.acquire();
        } else if (isBuzz(mod3, mod5)) {
          buzz.release(1);
          numPrint.acquire();
        } else if (isFizzBuzz(mod3, mod5)) {
          fizzbuzz.release(1);
          numPrint.acquire();
        } else {
          printNumber.accept(numInt);
        }
        num++;
      }
      fizz.release(1);
      buzz.release(1);
      fizzbuzz.release(1);
    }

    private boolean isFizz(int mod3, int mod5) {
      return mod3 == 0 && mod5 != 0;
    }

    private boolean isBuzz(int mod3, int mod5) {
      return mod3 != 0 && mod5 == 0;
    }

    private boolean isFizzBuzz(int mod3, int mod5) {
      return mod3 == 0 && mod5 == 0;
    }

    private boolean isOther(int mod3, int mod5) {
      return mod3 != 0 && mod5 != 0;
    }
  }

}

