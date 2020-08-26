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

package com.github.flysium.io.photon.collection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T01_HashMap {

  public static void main(String[] args) throws Throwable {
    System.out.println(getCapacity(new HashMap<>(0)));
    System.out.println(getCapacity(new HashMap<>(10)));
    System.out.println(getCapacity(new HashMap<>(16)));
    System.out.println(getCapacity(new HashMap<>(17)));
  }

  public static int getCapacity(HashMap<?, ?> m) throws Throwable {
    Method declaredMethod = HashMap.class.getDeclaredMethod("capacity");
    declaredMethod.setAccessible(true);
    MethodHandle handle = MethodHandles.lookup().unreflect(declaredMethod);
    return (int) handle.invoke(m);
  }

}
