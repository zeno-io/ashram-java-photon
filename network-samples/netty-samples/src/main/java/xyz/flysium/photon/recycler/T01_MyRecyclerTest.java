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

/**
 * object Pool for single thread.
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_MyRecyclerTest {

  public static final MyRecycler<MyObject> RECYCLER = new MyRecycler() {

    @Override
    public MyObject newObject(MyHandler handler) {
      return new MyObject(handler);
    }
  };

  public static void main(String[] args) {
    MyObject o = RECYCLER.get();
    o.name = "S";
    o.age = 18;
    o.recycle();

    MyObject o2 = RECYCLER.get();
    System.out.println(o2);

    RECYCLER.removeAll();
  }

}

class MyObject {

  private final MyHandler handler;

  String name;
  int age;

  public MyObject(MyHandler handler) {
    this.handler = handler;
  }

  public void recycle() {
    handler.recycle();
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("MyObject{");
    sb.append("name='").append(name).append('\'');
    sb.append(", age=").append(age);
    sb.append('}');
    return sb.toString();
  }
}

abstract class MyRecycler<T> {

  public MyRecycler() {

  }

  private final ThreadLocal<MyStack<T>> stackLocals = new ThreadLocal<MyStack<T>>() {
    @Override
    protected MyStack<T> initialValue() {
      return new MyStack<T>(10);
    }
  };

  public T get() {
    MyStack<T> myStack = stackLocals.get();
    MyDefaultHandler<T> handler = (MyDefaultHandler<T>) myStack.pop();
    if (handler == null) {
      handler = new MyDefaultHandler<T>(myStack);
      handler.value = newObject(handler);
    }
    return handler.value;
  }

  public void removeAll() {
    stackLocals.remove();
  }

  /**
   * 创建对象
   */
  public abstract T newObject(MyHandler handler);

  public void recycle(MyHandler handler) {
    handler.recycle();
  }

  private static class MyStack<T> {

    private final MyDefaultHandler<T>[] elements;
    private int size;

    MyStack(int size) {
      elements = new MyDefaultHandler[size];
      this.size = size;
    }

    MyDefaultHandler<T> pop() {
      size--;
      return elements[size];
    }

    void push(MyDefaultHandler<T> handler) {
      elements[size] = handler;
      size++;
    }

  }

  private static class MyDefaultHandler<T> implements MyHandler {

    private final MyStack<T> stack;
    private T value;

    MyDefaultHandler(MyStack<T> stack) {
      this.stack = stack;
    }

    @Override
    public void recycle() {
      stack.push(this);
    }
  }

}

interface MyHandler {

  /**
   * 回收对象
   */
  void recycle();
}