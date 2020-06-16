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

package com.github.flysium.io.sample.java2groovy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.codec.binary.Hex;

/**
 * Groovy Class Cache.
 *
 * @author Sven Augustus
 */
public class GroovyClassCache {

	/**
	 * Groovy Class Cache.
	 */
	private final Map<String, Class<?>> classCache;

	public GroovyClassCache() {
		classCache = new ConcurrentHashMap<>();
	}

	public String keyOf(String text) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
		return new String(Hex.encodeHex(messageDigest.digest(), true));
	}

	public Class<?> get(String key) {
		return classCache.get(key);
	}

	public Class<?> put(String key, Class<?> value) {
		return classCache.put(key, value);
	}

	public Class<?> putIfAbsent(String key, Class<?> value) {
		return classCache.putIfAbsent(key, value);
	}

}
