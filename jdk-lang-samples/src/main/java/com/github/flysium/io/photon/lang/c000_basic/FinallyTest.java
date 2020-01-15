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

package com.github.flysium.io.photon.lang.c000_basic;

/**
 * try{}里有一个return语句，那么紧跟在这个try后的finally{}里的代码会不会被执行，什么时候被执行，在return前还是后?
 *
 * @author Sven Augustus
 * @version 2016年10月30日
 */
public class FinallyTest {

	public static void main(String[] args) {
		System.out.println(testFinally());
	}

	/**
	 * 注意：在finally中改变返回值的做法是不好的， 因为如果存在finally代码块，try中的return语句不会立马返回调用者，
	 * 而是记录下返回值待finally代码块执行完毕之后再向调用者返回其值， 然后如果在finally中修改了返回值，就会返回修改后的值。
	 *
	 * 显然，在finally中返回或者修改返回值会对程序造成很大的困扰， C#中直接用编译错误的方式来阻止程序员干这种龌龊的事情，
	 * Java中也可以通过提升编译器的语法检查级别来产生警告或错误， Eclipse中可以在如图所示的地方进行设置，强烈建议将此项设置为编译错误。
	 */
	@SuppressWarnings("finally")
	static int testFinally() {
		int result = 0;
		try {
			// something to do
			result = 1;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			result = 2;
			return result;
		}
	}

}
