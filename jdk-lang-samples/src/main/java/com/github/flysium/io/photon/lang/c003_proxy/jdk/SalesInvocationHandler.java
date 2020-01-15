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

package com.github.flysium.io.photon.lang.c003_proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 销售代理调用器
 *
 * @author Sven Augustus
 */
public class SalesInvocationHandler implements InvocationHandler {

	private Object target; // 委托类对象；

	public SalesInvocationHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 这里可以做一下预处理
//		if(!proxy instanceof  GoodsProivder || !"provider".equals(method.getName())) {
//			throw new UnsupportedOperationException("不支持");
//		}
		System.out.println("欢迎您光临本店，您可以随意浏览和购买，祝您购物愉快...");
		Object result = method.invoke(this.target, args);
		System.out.println("欢迎您的光临，再见！...");
		return result;
	}
}
