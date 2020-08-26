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

package com.github.flysium.io.photon.lang.c002_reflect2;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * VarHandler 用法
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
public class T02_VarHandler2 {

  public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
    Demo inst = new Demo();

    VarHandle varHandle = MethodHandles.privateLookupIn(Demo.class, MethodHandles.lookup())
        .findVarHandle(Demo.class, "privateVar", int.class);
    //  读取访问模式(read access modes)
    // 获取指定内存排序效果下的变量值，包含的方法有get、getVolatile、getAcquire、getOpaque 。
    System.out.println("get -> " + varHandle.get(inst));
    System.out.println("getVolatile -> " + varHandle.getVolatile(inst));
    System.out.println("getAcquire -> " + varHandle.getAcquire(inst));
    System.out.println("getOpaque -> " + varHandle.getOpaque(inst));

    //  写入访问模式(write access modes)
    // 在指定的内存排序效果下设置变量的值，包含的方法有set、setVolatile、setRelease、setOpaque 。
    System.out.println("before set -> " + varHandle.get(inst));
    varHandle.set(inst, 33);
    System.out.println("after set -> " + varHandle.get(inst));

    //  原子更新模式(atomic update access modes)
    //  在指定的内存排序效果下，原子的比较和设置变量的值，包含的方法有 compareAndSet、weakCompareAndSetPlain、weakCompareAndSet、weakCompareAndSetAcquire、weakCompareAndSetRelease、compareAndExchangeAcquire、compareAndExchange、compareAndExchangeRelease、getAndSet、getAndSetAcquire、getAndSetRelease
    System.out.println("before compareAndSet -> " + varHandle.get(inst));
    varHandle.compareAndSet(inst, 33, 66);
    System.out.println("after compareAndSet -> " + varHandle.get(inst));

    //  数值更新访问模式(numeric atomic update access modes)数字原子更新访问模式
    //  在指定的内存排序效果下添加变量的值，以原子方式获取和设置。 包含的方法有getAndAdd、getAndAddAcquire、getAndAddRelease 。
    System.out.println("before getAndAdd -> " + varHandle.get(inst));
    varHandle.getAndAdd(inst, 4);
    System.out.println("after getAndAdd -> " + varHandle.get(inst));

    // 按位原子更新访问模式(bitwise atomic update access modes )
    // 在指定的内存排序效果下，以原子方式获取和按位OR变量的值。 包含的方法有getAndBitwiseOr、getAndBitwiseOrAcquire、getAndBitwiseOrRelease、 getAndBitwiseAnd、getAndBitwiseAndAcquire、getAndBitwiseAndRelease、getAndBitwiseXor、getAndBitwiseXorAcquire ， getAndBitwiseXorRelease 。
  }

  static class Demo {

    private volatile int privateVar = 3;
  }

}
