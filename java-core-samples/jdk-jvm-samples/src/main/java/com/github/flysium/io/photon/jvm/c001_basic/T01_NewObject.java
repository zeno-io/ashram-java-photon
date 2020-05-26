/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
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

package com.github.flysium.io.photon.jvm.c001_basic;

/**
 * T01_NewObject t = new T01_NewObject() 的字节码
 */
public class T01_NewObject {

  int m = 8;

  public static void main(String[] args) {
    T01_NewObject t = new T01_NewObject();
//        new :  申请内存 - 默认值 - 初始值
//        invokerspecial  T01_NewObject.<init> : 调用默认构造函数
//        astrore :  赋值给 T01_NewObject t
  }
}
