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

package xyz.flysium.photon.c008_ref_and_threadlocal;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 虚引用
 *
 * @author Sven Augustus
 */
public class V04_PhantomReference {

  //  “虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。
  //  如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。

  //-Xmx10m
  public static void main(String[] args) throws IOException, InterruptedException {
    M m = new M("Sven Augustus", 20);
    ReferenceQueue<M> referenceQueue = new ReferenceQueue<M>();
    PhantomReference<M> phantomReference = new PhantomReference<>(m, referenceQueue);
    m = null;
    final List<Object> LIST = new LinkedList<>();

    new Thread(() -> {
      while (true) {
        Reference<? extends M> o = referenceQueue.poll();
        if (o != null) {
          System.out.println("虚引用对象被回收了！" + o);
        }
      }
    }).start();

    new Thread(() -> {
      while (true) {
        LIST.add(new byte[1024 * 1024]);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
        }
        System.out.println(phantomReference.get());
      }
    }).start();

  }

}
