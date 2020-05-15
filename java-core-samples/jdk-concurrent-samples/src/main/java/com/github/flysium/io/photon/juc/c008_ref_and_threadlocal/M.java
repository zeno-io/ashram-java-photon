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

/**
 * Entity
 *
 * @author Sven Augustus
 */
public class M {

  private String name;
  private int age;

  public M(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  protected void finalize() throws Throwable {
    System.out.println("我被回收了！" + toString());
  }

  @Override
  public String toString() {
    return "M{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}
