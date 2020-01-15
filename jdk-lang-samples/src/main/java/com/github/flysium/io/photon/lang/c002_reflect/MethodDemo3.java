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

public class MethodDemo3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User u1 = new User("zhangsan", "123456", 30);
		System.out.println(BeanUtil.getValueByPropertyName(u1, "username"));
		System.out.println(BeanUtil.getValueByPropertyName(u1, "userpass"));
	}

}
