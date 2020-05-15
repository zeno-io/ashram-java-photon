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
 * @author Sven Augustus
 * @version 2016年11月20日
 */
public class Person
		implements
		java.io.Serializable/* , java.io.ObjectInputValidation */ {

	public Person(String fn, String ln, int a) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public Person getSpouse() {
		return spouse;
	}

	public void setFirstName(String value) {
		firstName = value;
	}

	public void setLastName(String value) {
		lastName = value;
	}

	public void setAge(int value) {
		age = value;
	}

	public void setSpouse(Person value) {
		spouse = value;
	}

	public String toString() {
		return "[Person: firstName=" + firstName + " lastName=" + lastName + " age=" + age
				+ " spouse="
				+ spouse.getFirstName() + "]";
	}

	private String firstName;
	private String lastName;
	private int age;
	private Person spouse;

	/** 1. 序列化允许重构 */
	/*
	 * private String sss;
	 *
	 * public String getSss() { return sss; }
	 *
	 * public void setSss(String sss) { this.sss = sss; }
	 */

	/** 2. 序列化并不安全 */
	/*
	 * private void writeObject(java.io.ObjectOutputStream stream) throws
	 * java.io.IOException { // "Encrypt"/obscure the sensitive data age = age
	 * << 2; stream.defaultWriteObject(); }
	 *
	 * private void readObject(java.io.ObjectInputStream stream) throws
	 * java.io.IOException, ClassNotFoundException {
	 * stream.registerValidation(this, 1);//// 注册验证插件
	 * stream.registerValidation(new ObjectInputValidation() {
	 *
	 * @Override public void validateObject() throws InvalidObjectException { if
	 * (age < 40) { // 我们制作校验 throw new
	 * InvalidObjectException("age is not right."); } } }, 1);//// 注册验证插件
	 * stream.defaultReadObject(); // "Decrypt"/de-obscure the sensitive data
	 * age = age >> 2; }
	 */

	/** 3.序列化允许将代理放在流中 */
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new PersonProxy(this);
	}

	/** 4.信任，但要验证 */
	/*
	 * (non-Javadoc)
	 *
	 * @see java.io.ObjectInputValidation#validateObject()
	 */
	/*
	 * @Override public void validateObject() throws InvalidObjectException { if
	 * (age < 0) { //if (age < 40) { // 我们制作校验 throw new
	 * InvalidObjectException("age is not right."); } }
	 */

}