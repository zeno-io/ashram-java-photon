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

package com.github.flysium.io.photon.lang.c003_proxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 销售方法拦截器接口
 *
 * @author Sven Augustus
 */
public class SalesMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy)
			throws Throwable {
		// 这里可以做一下预处理
//		if(!(target instanceof GoodsProivder) || !"provider".equals(method.getName())) {
//			return null;
//		}
		System.out.println("欢迎您光临本店，您可以随意浏览和购买，祝您购物愉快...");
		//Object result=proxy.invoke(target, args);
		Object result = proxy.invokeSuper(target, args);// 表示调用原始类的被拦截到的方法。
		System.out.println("欢迎您的光临，再见！...");
		return result;
	}
}
