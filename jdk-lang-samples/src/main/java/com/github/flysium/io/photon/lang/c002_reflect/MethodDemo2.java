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

package com.github.flysium.io.photon.lang.c002_reflect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class MethodDemo2 {

	public static void main(String[] args) {
		UserService us = new UserService();
		/*
		 * 通过键盘输入命令执行操作 输入update命令就调用update方法 输入delete命令就调用delete方法 ...
		 */
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("请输入命令:");
			String action = br.readLine();
			/*
			 * if("update".equals(action)){ us.update(); }
			 * if("delete".equals(action)){ us.delete(); }
			 * if("find".equals(action)){ us.find(); }
			 */
			/*
			 * action就是方法名称， 都没有参数--->通过方法的反射操作就会简单很多 通过方法对象然后进行反射操作
			 */
			Class<? extends UserService> c = us.getClass();
			Method m = c.getMethod(action);
			m.invoke(us);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
