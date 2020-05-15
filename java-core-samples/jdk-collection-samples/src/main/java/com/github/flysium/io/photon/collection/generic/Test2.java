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

package com.github.flysium.io.photon.collection.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  获取运行时的泛型类型信息
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
