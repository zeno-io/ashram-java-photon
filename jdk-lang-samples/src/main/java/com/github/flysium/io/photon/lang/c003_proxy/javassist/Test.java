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

import com.github.flysium.io.photon.lang.c003_proxy.statics.GoodsProivder;
import com.github.flysium.io.photon.lang.c003_proxy.statics.GoodsProivderImpl;
import java.lang.reflect.Method;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

/**
 * javassist
 *
 * @author Sven Augustus
 */
public class Test {

	public static void main(String[] args) throws IllegalAccessException, InstantiationException {
		ProxyFactory f = new ProxyFactory();
		// 设置被代理类
		f.setSuperclass(GoodsProivderImpl.class);
		// 设置方法过滤器
		f.setFilter(new MethodFilter() {
			public boolean isHandled(Method m) {
				// ignore finalize()
				return !m.getName().equals("finalize");
			}
		});
		// 创建代理类
		Class c = f.createClass();
		GoodsProivder proxy = (GoodsProivder) c.newInstance();
		// 设置方法调用处理器
		((Proxy) proxy).setHandler(new SaleMethodHandler());
		proxy.provider();
	}
}