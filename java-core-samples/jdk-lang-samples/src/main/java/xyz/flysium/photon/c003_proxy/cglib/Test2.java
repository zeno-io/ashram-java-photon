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

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import xyz.flysium.photon.c003_proxy.statics.GoodsProivder;
import xyz.flysium.photon.c003_proxy.statics.GoodsProivderImpl;

/**
 * CGLIB代理的例子
 *
 * @author Sven Augustus
 */
public class Test2 {

	public static void main(String[] args) {
		// cglib 中加强器
		Enhancer enhancer = new Enhancer();
		// 设置要创建动态代理的类
		enhancer.setSuperclass(GoodsProivderImpl.class);
		// 设置回调，这里相当于是对于代理类上所有方法的调用
		enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, new SalesMethodInterceptor()});
		// 设置回调过滤器
		enhancer.setCallbackFilter(new CallbackFilter() {

			@Override
			public int accept(Method method) {
				String methodName = method.getName();
				//System.out.println("--------"+methodName);
				if ("ad".equals(methodName)) {
					return 0; // toString()方法使用callbacks[0]对象拦截。
				} else if ("provider".equals(methodName)) {
					return 1; // provider()方法使用callbacks[1]对象拦截。
				}
				return 0;
			}
		});
		// 创建动态代理类实例
		GoodsProivder proxy = (GoodsProivder) enhancer.create();
		proxy.provider();
		proxy.ad();
	}
}

