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

package xyz.flysium.photon.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取运行时的泛型类型信息
 *
 * @author Sven Augustus
 */
public class Test2 {

	static class ParameterizedTypeReference<T> {

		protected final Type type;

		public ParameterizedTypeReference() {
			Type superClass = this.getClass().getGenericSuperclass();
			//if (superClass instanceof Class) {
			// throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
			//	} else {
			this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
			//}
		}

		public Type getType() {
			return type;
		}
	}

	public static void main(String[] args) {
		// System.out.println(new ParameterizedTypeReference<String>().getType());
		// java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
		// 此处会输出报错，因此ParameterizedTypeReference 应不能直接实例化，可以考虑加abstract

		System.out.println(new ParameterizedTypeReference<String>() {
		}.getType());
		// ParameterizedTypeReference 的匿名内部类，可以触发super()，即 ParameterizedTypeReference()的构造器逻辑，正常运行
	}

}
