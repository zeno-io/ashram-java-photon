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

import com.github.flysium.io.photon.lang.c003_proxy.statics.GoodsProivder;
import com.github.flysium.io.photon.lang.c003_proxy.statics.GoodsProivderImpl;
import net.sf.cglib.proxy.Enhancer;

/**
 * CGLIB代理的例子
 *
 * @author Sven Augustus
 */
public class Test {

	public static void main(String[] args) {
//		GoodsProivder proxy = new SalesProxy(new GoodsProivderImpl());
		// 利用 功能实现类，以及调用器，生成代理类实例
//		GoodsProivder proxy = (GoodsProivder) Proxy.newProxyInstance(GoodsProivder.class.getClassLoader(),
//				new Class[]{GoodsProivder.class},
//				new SalesInvocationHandler(new GoodsProivderImpl()));
		// cglib 中加强器
		Enhancer enhancer = new Enhancer();
		// 设置要创建动态代理的类
		enhancer.setSuperclass(GoodsProivderImpl.class);
		// 设置回调，这里相当于是对于代理类上所有方法的调用
		enhancer.setCallback(new SalesMethodInterceptor());
		// 创建动态代理类实例
		GoodsProivder proxy = (GoodsProivder) enhancer.create();
		proxy.provider();
	}
}
