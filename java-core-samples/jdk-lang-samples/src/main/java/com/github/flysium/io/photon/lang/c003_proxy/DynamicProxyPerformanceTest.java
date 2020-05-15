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

package com.github.flysium.io.photon.lang.c003_proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.DecimalFormat;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 动态代理方案性能对比
 * @author Sven Augustus
 */
public class DynamicProxyPerformanceTest {

	public static void main(String[] args) throws Exception {
		CountService target = new CountServiceImpl();

		long time = System.currentTimeMillis();
		CountService jdkProxy = createJdkDynamicProxy(target);
		time = System.currentTimeMillis() - time;
		System.out.println("Create JDK Proxy: " + time + " ms");

		time = System.currentTimeMillis();
		CountService cglibProxy = createCglibDynamicProxy(target);
		time = System.currentTimeMillis() - time;
		System.out.println("Create CGLIB Proxy: " + time + " ms");

		time = System.currentTimeMillis();
		CountService javassistProxy = createJavasssistDynamicProxy(target);
		time = System.currentTimeMillis() - time;
		System.out.println("Create JAVAASSIST Proxy: " + time + " ms");

		time = System.currentTimeMillis();
		CountService javassistBytecodeProxy = createJavassistBytecodeDynamicProxy(target);
		time = System.currentTimeMillis() - time;
		System.out.println("Create JAVAASSIST Bytecode Proxy: " + time + " ms");

		System.out.println("----------------");
		int times = 5;
		long jdk = 0, cglib = 0, javassist = 0, javassistBytecode = 0;
		for (int i = 0; i < times; i++) {
			jdk += test(jdkProxy, "Run JDK Proxy: ");
			cglib += test(cglibProxy, "Run CGLIB Proxy: ");
			javassist += test(javassistProxy, "Run JAVAASSIST Proxy: ");
			javassistBytecode += test(javassistBytecodeProxy, "Run JAVAASSIST Bytecode Proxy: ");
			System.out.println("----------------");
		}
		System.out.println("Run JDK Proxy Average: " + jdk / (times * 1.0) + " ms");
		System.out.println("Run CGLIB Proxy Average: " + cglib / (times * 1.0) + " ms");
		System.out.println("Run JAVAASSIST Proxy Average: " + javassist / (times * 1.0) + " ms");
		System.out.println(
				"Run JAVAASSIST Bytecode Proxy Average: " + javassistBytecode / (times * 1.0)
						+ " ms");
	}

	private static long test(CountService proxy, String title) {
		proxy.count(); // warm up
		int times = 10000000;
		long time = System.currentTimeMillis();
		for (int i = 1; i < times; ++i) {
			proxy.count();
		}
		time = System.currentTimeMillis() - time;
		System.out.println(
				title + time + " ms, " + new DecimalFormat().format(times * 1000 / time) + " t/s");
		return time;
	}

	// jdk动态代理
	public static <T> T createJdkDynamicProxy(final Object target) {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						return method.invoke(target, args);
					}
				});
	}

	// cglib动态代理
	public static <T> T createCglibDynamicProxy(final Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new MethodInterceptor() {

			@Override
			public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy)
					throws Throwable {
				return proxy.invokeSuper(target, args);
			}
		});
		return (T) enhancer.create();
	}

	// javasssist动态代理
	public static <T> T createJavasssistDynamicProxy(final Object target)
			throws IllegalAccessException, InstantiationException {
		ProxyFactory f = new ProxyFactory();
		f.setSuperclass(target.getClass());
//		f.setFilter(new MethodFilter() {
//			public boolean isHandled(Method m) {
//				return !m.getName().equals("finalize");// ignore finalize()
//			}
//		});
		Class c = f.createClass();
		T proxy = (T) c.newInstance();
		((javassist.util.proxy.Proxy) proxy).setHandler(new MethodHandler() {

			@Override
			public Object invoke(Object self, Method m, Method proceed, Object[] args)
					throws Throwable {
				return proceed.invoke(self, args);
			}
		});
		return proxy;
	}

	// javasssist字节码动态代理
	private static CountService createJavassistBytecodeDynamicProxy(CountService delegate)
			throws Exception {
		ClassPool mPool = new ClassPool(true);
		CtClass mCtc = mPool.makeClass(CountService.class.getName() + "JavaassistProxy");
		mCtc.addInterface(mPool.get(CountService.class.getName()));
		mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
		mCtc.addField(CtField.make("public " + CountService.class.getName() + " delegate;", mCtc));
		mCtc.addMethod(CtNewMethod.make("public void count() { return delegate.count(); }", mCtc));
		Class pc = mCtc.toClass();
		CountService bytecodeProxy = (CountService) pc.newInstance();
		Field filed = bytecodeProxy.getClass().getField("delegate");
		filed.set(bytecodeProxy, delegate);
		return bytecodeProxy;
	}

}
