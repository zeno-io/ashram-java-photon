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

package xyz.flysium.photon.c002_reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MethodDemo4 {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) {
		ArrayList list = new ArrayList();

		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("hello");
		// list1.add(20);错误的
		Class<? extends ArrayList> c1 = list.getClass();
		Class c2 = list1.getClass();
		System.out.println(c1 == c2);
		// 反射的操作都是编译之后的操作

		/*
		 * c1==c2结果返回true说明编译之后集合的泛型是去泛型化的 Java中集合的泛型，是防止错误输入的，只在编译阶段有效，
		 * 绕过编译就无效了 验证：我们可以通过方法的反射来操作，绕过编译
		 */
		try {
			Method m = c2.getMethod("add", Object.class);
			m.invoke(list1, 20);// 绕过编译操作就绕过了泛型
			System.out.println(list1.size());
			System.out.println(list1);
			/*
			 * for (String string : list1) { System.out.println(string); }
			 */// 现在不能这样遍历
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
