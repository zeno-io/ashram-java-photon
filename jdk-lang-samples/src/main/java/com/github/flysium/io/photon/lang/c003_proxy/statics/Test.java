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

package com.github.flysium.io.photon.lang.c003_proxy.statics;

/**
 * 静态代理的例子
 *
 * @author Sven Augustus
 */
public class Test {

	public static void main(String[] args) {
		// 这里对于客户而言，销售代理是可见的，厂商是不可见的
		GoodsProivder proxy = new SalesProxy(new GoodsProivderImpl());
		proxy.provider();
	}
}
