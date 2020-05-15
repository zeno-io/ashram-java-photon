package com.github.flysium.io.photon.juc.c001_thread;

/**
 * 线程状态 NEW > RUNNABLE (BLOCKED  \ WAITING \ TIMED_WAITING)  > TERMINATED
 *
 * @author Sven Augustus
 */
public class T09_ThreadState {

  static class MyThread extends Thread {

    private final Object lockObject;
    private final boolean flag;

    public MyThread(Object lockObject, boolean flag) {
      super();
      this.lockObject = lockObject;
      this.flag = flag;
      this.setName(flag ? "线程t1" : "线程t2");
      System.out.println(
          Thread.currentThread().getName() + " " + this.getName() + " 构造方法状态：" + this
              .getState());
    }

    @Override
    public void run() {
      synchronized (lockObject) {
        System.out.println(Thread.currentThread().getName()
            + " run()方法状态：" + Thread.currentThread().getState());
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (flag) {
          try {
            lockObject.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          lockObject.notify();
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Object lockObject = new Object();

    MyThread t1 = new MyThread(lockObject, true);
    System.out.println(Thread.currentThread().getName() + " 线程t1 start()前状态：" + t1.getState());
    t1.start();
    Thread.sleep(100);
    MyThread t2 = new MyThread(lockObject, false);
    System.out.println(Thread.currentThread().getName() + " 线程t2 start()前状态：" + t1.getState());
    t2.start();
    Thread.sleep(1000);
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行时状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行时状态：" + t2.getState());
    Thread.sleep(4000);
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行后状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行后状态：" + t1.getState());
    t2.join();
    t1.join();
    System.out.println(Thread.currentThread().getName() + " 线程t1 运行后状态：" + t1.getState());
    System.out.println(Thread.currentThread().getName() + " 线程t2 运行后状态：" + t1.getState());
  }

}
