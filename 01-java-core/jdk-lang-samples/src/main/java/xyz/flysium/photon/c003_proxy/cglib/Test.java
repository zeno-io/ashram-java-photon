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

package xyz.flysium.photon.c003_proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import xyz.flysium.photon.c003_proxy.statics.GoodsProivder;
import xyz.flysium.photon.c003_proxy.statics.GoodsProivderImpl;

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
