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

package com.github.flysium.io.photon.juc.c003_volatile;

/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 *
 * @author Sven Augustus
 */
public class T04_PrintAssembly__Volatile {

  volatile boolean running = true;
  volatile static T04_PrintAssembly__Volatile t = new T04_PrintAssembly__Volatile();

  // -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly
  // 修改没有volatile修饰对象 没有 lock , 修改volatile对象 有 lock
  // 修改volatile对象没有volatile修饰的属性 没有 lock , 修改volatile对象有volatile修饰的属性 有 lock
  public static void main(String[] args) {
    //t=null;
    t.running = false;

//    if (t == null) {
//
//    }
//   if(t.running){
//
//   }

  }

}
