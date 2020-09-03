/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.recycler;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

/**
 * object Pool for multiple threads.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T02_MyRecyclerTest {

  public static final MyRecycler2<MyObject2> RECYCLER = new MyRecycler2() {
    @Override
    public MyObject2 newObject(MyHandler2 handler) {
      return new MyObject2(handler);
    }
  };

  public static void main(String[] args) throws InterruptedException {
    final MyObject2 o1 = RECYCLER.get();
    o1.name = "X";
    o1.age = 18;
    final MyObject2 o2 = RECYCLER.get();
    o2.name = "Y";
    o2.age = 28;
    final MyObject2 o3 = RECYCLER.get();
    o3.name = "Z";
    o3.age = 38;

    System.out.println(o1.hashCode() + ", " + o1);
    System.out.println(o2.hashCode() + ", " + o2);
    System.out.println(o3.hashCode() + ", " + o3);

    Thread t1 = new Thread(() -> {
      o1.recycle();
      o2.recycle();
      o3.recycle();
    });

    t1.start();
    t1.join();

    MyObject2 m1 = RECYCLER.get();
    MyObject2 m2 = RECYCLER.get();
    MyObject2 m3 = RECYCLER.get();

    System.out.println(m1.hashCode() + ", " + m1);
    System.out.println(m2.hashCode() + ", " + m2);
    System.out.println(m3.hashCode() + ", " + m3);

    RECYCLER.removeAll();
  }

}

class MyObject2 {

  private final MyHandler2 handler;

  String name;
  int age;

  public MyObject2(MyHandler2 handler) {
    this.handler = handler;
  }

  public void recycle() {
    handler.recycle();
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("MyObject2{");
    sb.append("name='").append(name).append('\'');
    sb.append(", age=").append(age);
    sb.append('}');
    return sb.toString();
  }
}

abstract class MyRecycler2<T> {

  public MyRecycler2() {

  }

  private final ThreadLocal<MyStack2<T>> stackLocals = new ThreadLocal<MyStack2<T>>() {
    @Override
    protected MyStack2<T> initialValue() {
      return new MyStack2<T>(10);
    }

  };

  public T get() {
    MyStack2<T> myStack = stackLocals.get();
    MyDefaultHandler2<T> handler = (MyDefaultHandler2<T>) myStack.pop();
    if (handler == null) {
      handler = new MyDefaultHandler2<T>(myStack);
      handler.value = newObject(handler);
    }
    return handler.value;
  }

  public void removeAll() {
    stackLocals.remove();
    WQLOCALS.remove();
  }

  /**
   * 创建对象
   */
  public abstract T newObject(MyHandler2 handler);

  public void recycle(MyHandler2 handler) {
    handler.recycle();
  }

  private static class MyStack2<T> {

    private final MyDefaultHandler2<T>[] elements;
    private int size;
    // 为防止 Thread 线程结束，导致不能及时 GC，这里设计为 WeakReference
    private final WeakReference<Thread> threadRef;

    private volatile MyWeakOrderQueue2 head;

    MyStack2(int size) {
      elements = new MyDefaultHandler2[size];
      this.size = 0;
      this.threadRef = new WeakReference<>(Thread.currentThread());
    }

    MyDefaultHandler2<T> pop() {
      if (this.size == 0) {
        if (!scavenge()) {
          return null;
        }
        if (size <= 0) {
          return null;
        }
      }
      size--;
      return elements[size];
    }

    synchronized void setHead(MyWeakOrderQueue2 queue) {
      queue.setNext(head);
      this.head = queue;
    }

    // FIXME
    private boolean scavenge() {
      MyWeakOrderQueue2 cursor = this.head;
      if (cursor == null) {
        return false;
      }
      boolean success = false;
      do {
        if (cursor.transfer(this)) {
          success = true;
          break;
        }
        cursor = cursor.getNext();

      } while (cursor != null && !success);

      return success;
    }

    void push(MyDefaultHandler2<T> handler) {
      Thread thread = Thread.currentThread();
      if (threadRef.get() == thread) {
        pushNow(handler);
      } else {
        pushLater(handler, thread);
      }
    }

    private void pushNow(MyDefaultHandler2<T> handler) {
      elements[size] = handler;
      size++;
    }

    private void pushLater(MyDefaultHandler2<T> handler, Thread thread) {
      MyStack2<T> stack = handler.stack;
      Map<Stack, MyWeakOrderQueue2> map = (Map<Stack, MyWeakOrderQueue2>) WQLOCALS.get();
      MyWeakOrderQueue2 queue = map.get(stack);
      if (queue == null) {
        queue = new MyWeakOrderQueue2(Thread.currentThread());
        stack.setHead(queue);
      }
      queue.add(handler);
    }

  }

  private static class MyDefaultHandler2<T> implements MyHandler2 {

    private final MyStack2<T> stack;
    private T value;

    MyDefaultHandler2(MyStack2<T> stack) {
      this.stack = stack;
    }

    @Override
    public void recycle() {
      // FIXME 避免重复回收对象
      stack.push(this);
    }
  }

  private static final ThreadLocal<Map<Stack, MyWeakOrderQueue2>> WQLOCALS = new ThreadLocal<Map<Stack, MyWeakOrderQueue2>>() {
    @Override
    protected Map<Stack, MyWeakOrderQueue2> initialValue() {
      return new WeakHashMap<>();
    }
  };

  // 为防止 Thread 线程结束，导致不能及时 GC，这里设计为 WeakReference
  private static class MyWeakOrderQueue2 extends WeakReference<Thread> {

    private MyWeakOrderQueue2 next;

    private final LinkedList<MyDefaultHandler2> linkedList = new LinkedList<MyDefaultHandler2>();

    public MyWeakOrderQueue2(Thread referent) {
      super(referent);
    }

    public void setNext(MyWeakOrderQueue2 next) {
      this.next = next;
    }

    public MyWeakOrderQueue2 getNext() {
      return next;
    }

    public void add(MyDefaultHandler2 handler) {
      linkedList.add(handler);
    }

    // FIXME 后续改为无锁化操作
    public synchronized boolean transfer(MyStack2 stack) {
      if (linkedList.isEmpty()) {
        return false;
      }
      for (MyDefaultHandler2 myHandler2 : linkedList) {
        stack.push(myHandler2);
      }
      linkedList.clear();
      return true;
    }

  }

}

interface MyHandler2 {

  /**
   * 回收对象
   */
  void recycle();
}