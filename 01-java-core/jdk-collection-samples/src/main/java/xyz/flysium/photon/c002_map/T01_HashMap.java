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

package xyz.flysium.photon.c002_map;

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
