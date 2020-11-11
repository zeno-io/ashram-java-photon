package xyz.flysium.photon.concurrent.medium;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;
import xyz.flysium.photon.concurrent.TestSupport;

/**
 * 1195. 交替打印字符串
 * <p>
 * https://leetcode-cn.com/problems/fizz-buzz-multithreaded/
 *
 * @author zeno
 */
public class T1195_FizzBuzzMultithreadedV2_Lock {

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
    private final Lock lock = new ReentrantLock();
    private final Condition fizz = lock.newCondition();
    private final Condition buzz = lock.newCondition();
    private final Condition fizzbuzz = lock.newCondition();
    private final Condition numPrint = lock.newCondition();
    private volatile int num = 1;

    public FizzBuzz(int n) {
      this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
      lock.lock();
      try {
        if (!isFizz(num % 3, num % 5)) {
          fizz.await();
        }
        while (num <= n) {
          printFizz.run();
          numPrint.signal();
          fizz.await();
        }
      } finally {
        lock.unlock();
      }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
      lock.lock();
      try {
        if (!isBuzz(num % 3, num % 5)) {
          buzz.await();
        }
        while (num <= n) {
          printBuzz.run();
          numPrint.signal();
          buzz.await();
        }
      } finally {
        lock.unlock();
      }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
      lock.lock();
      try {
        if (!isFizzBuzz(num % 3, num % 5)) {
          fizzbuzz.await();
        }
        while (num <= n) {
          printFizzBuzz.run();
          numPrint.signal();
          fizzbuzz.await();
        }
      } finally {
        lock.unlock();
      }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
      lock.lock();
      try {
        while (num <= n) {
          int numInt = num;
          int mod3 = numInt % 3;
          int mod5 = numInt % 5;
          // 统一由 num 线程调度唤醒其他线程，其他线程预先 await，等待唤醒
          if (isFizz(mod3, mod5)) {
            fizz.signalAll();
            numPrint.await();
          } else if (isBuzz(mod3, mod5)) {
            buzz.signalAll();
            numPrint.await();
          } else if (isFizzBuzz(mod3, mod5)) {
            fizzbuzz.signalAll();
            numPrint.await();
          } else {
            printNumber.accept(numInt);
          }
          num++;
        }
        fizz.signalAll();
        buzz.signalAll();
        fizzbuzz.signalAll();
      } finally {
        lock.unlock();
      }
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

