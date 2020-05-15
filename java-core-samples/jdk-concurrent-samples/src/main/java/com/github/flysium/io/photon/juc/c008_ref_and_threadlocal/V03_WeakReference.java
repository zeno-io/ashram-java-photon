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
import java.lang.ref.WeakReference;

/**
 * 弱引用
 *
 * @author Sven Augustus
 */
public class V03_WeakReference {

  // 一个对象如果只有弱引用，遭到gc就会回收（如果还有有强引用，自然不会被回收）
  // 弱引用与软引用的区别在于：只具有弱引用的对象拥有更短暂的生命周期。
  // 在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。
  // 不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。

  //  弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。

  public static void main(String[] args) throws IOException, InterruptedException {
    M m = new M("Sven Augustus", 20);
    WeakReference<M> wr = new WeakReference<>(m);
    m = null;

    System.out.println(wr.get());
    System.gc();
    System.out.println(wr.get());
  }

}
