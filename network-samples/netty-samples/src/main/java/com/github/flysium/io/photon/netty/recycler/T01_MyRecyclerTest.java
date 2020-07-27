/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.netty.recycler;

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