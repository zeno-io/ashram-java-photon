/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.juc.c008_ref_and_threadlocal;

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
