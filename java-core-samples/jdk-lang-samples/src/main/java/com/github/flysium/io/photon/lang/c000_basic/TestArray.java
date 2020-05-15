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

package com.github.flysium.io.photon.lang.c000_basic;

/**
 * 数组的创建
 *
 * @author Sven Augustus
 */
public class TestArray {

  public static void main(String[] args) {
    float[] f1[] = new float[6][6];
//		float f2[][] = new float[][];
//		float [6][]f3 = new float[6][6];
    float[][] f4 = new float[6][];

    System.out.println(f1.length);
    System.out.println(f4.length);
    // NullPointerException
    //  System.out.println(f4[0].length);
  }

}
