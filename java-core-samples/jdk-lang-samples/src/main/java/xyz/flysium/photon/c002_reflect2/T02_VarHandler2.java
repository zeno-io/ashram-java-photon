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

package xyz.flysium.photon.c002_reflect2;

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
