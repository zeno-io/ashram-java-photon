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
