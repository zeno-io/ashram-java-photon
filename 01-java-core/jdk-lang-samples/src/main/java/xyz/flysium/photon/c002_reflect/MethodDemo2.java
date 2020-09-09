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

package xyz.flysium.photon.c002_reflect;

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
