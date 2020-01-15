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

package com.github.flysium.io.photon.inoutput.c001_serializable;

/**
 * 序列化允许将代理放在流中
 *
 * 为原始 Person 提供一个 writeReplace 方法，可以序列化不同类型的对象来代替它。 类似地，如果反序列化期间发现一个
 * readResolve 方法，那么将调用该方法，将替代对象提供给调用者。
 *
 * @author Sven Augustus
 * @version 2016年11月20日
 */
public class PersonProxy implements java.io.Serializable {

	private static final long serialVersionUID = -671942554578006397L;

	public PersonProxy(Person orig) {
		data = orig.getFirstName() + "," + orig.getLastName() + "," + orig.getAge();
		if (orig.getSpouse() != null) {
			Person spouse = orig.getSpouse();
			data = data + "," + spouse.getFirstName() + "," + spouse.getLastName() + "," + spouse
					.getAge();
		}
	}

	public String data;

	private Object readResolve() throws java.io.ObjectStreamException {
		String[] pieces = data.split(",");
		Person result = new Person(pieces[0], pieces[1], Integer.parseInt(pieces[2]));
		if (pieces.length > 3) {
			result.setSpouse(new Person(pieces[3], pieces[4], Integer.parseInt(pieces[5])));
			result.getSpouse().setSpouse(result);
		}
		return result;
	}

}