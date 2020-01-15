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

package com.github.flysium.io.photon.lang.c003_proxy.javassist;

import java.lang.reflect.Method;
import javassist.util.proxy.MethodHandler;

/**
 * @author Sven Augustus
 */
public class SaleMethodHandler implements MethodHandler {

	public Object invoke(Object self, Method m, Method proceed, Object[] args) throws Throwable {
		System.out.println("欢迎您光临本店，您可以随意浏览和购买，祝您购物愉快...");
		//Object result = m.invoke(delegate, args);
		Object result = proceed.invoke(self, args); // execute the original method.
		System.out.println("欢迎您的光临，再见！...");
		return result;
	}
}
