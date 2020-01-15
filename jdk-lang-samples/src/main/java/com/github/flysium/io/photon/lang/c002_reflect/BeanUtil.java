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

package com.github.flysium.io.photon.lang.c002_reflect;

import java.lang.reflect.Method;

public class BeanUtil {

	/**
	 * 根据标准javaBean对象的属性名获取其属性值
	 *
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static Object getValueByPropertyName(Object obj, String propertyName) {
		// 1.根据属性名称就可以获取其get方法
		String getMethodName =
				"get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		// 2.获取方法对象
		Class<? extends Object> c = obj.getClass();
		try {
			// get方法都是public的且无参数
			Method m = c.getMethod(getMethodName);
			// 3 通过方法的反射操作方法
			Object value = m.invoke(obj);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
