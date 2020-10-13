package xyz.flysium.photon.concurrent;

/**
 * 1114. 按序打印
 * <p>
 * https://leetcode-cn.com/problems/print-in-order
 *
 * @author zeno
 */
public class T1114_PrintInOrderV1_WaitNotify extends T1114_PrintInOrder_CAS {

  private static final int TIMES = 1000;

  public static void main(String[] args) throws InterruptedException {
    long l = TestSupport.testWithMyConsumer(TIMES, Foo::new, (foo) -> {
      foo.first(() -> System.out.print("first"));
    }, (foo) -> {
      foo.second(() -> System.out.print("second"));
    }, (foo) -> {
      foo.third(() -> System.out.print("third"));
    });
    System.out.println();
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class Foo {

    // 1 -> first, 2 -> second, 3 -> third
    private short flag = 1;

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
      synchronized (this) {
        while (flag != 1) {
          wait();
        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();

        flag = 2;
        notifyAll();
      }
    }

    public void second(Runnable printSecond) throws InterruptedException {
      synchronized (this) {
        while (flag != 2) {
          wait();
        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();

        flag = 3;
        notifyAll();
      }
    }

    public void third(Runnable printThird) throws InterruptedException {
      synchronized (this) {
        while (flag != 3) {
          wait();
        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
      }
    }
  }

}
