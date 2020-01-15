
/*
 * Copyright (c) 2017-2030, Sven Augustus (Sven Augustus@outlook.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.flysium.io.photon.inoutput.c002_nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * ByteBuffer工具类
 *
 * @author Sven Augustus
 */
public class ByteBufferUtils {

	private ByteBufferUtils() {
	}

	/**
	 * 序列化ByteBuffer
	 *
	 * @param serializable
	 *            可序列化的对象
	 * @return 字节缓冲区
	 * @throws IOException
	 */
	public static ByteBuffer writeObject(Serializable serializable) throws IOException {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(new BufferedOutputStream(bos));
			oos.writeObject(serializable);
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
		// System.out.println(printByteArray(bos.toByteArray()));
		return ByteBuffer.wrap(bos.toByteArray());
	}

	/**
	 * 反序列化ByteBuffer
	 *
	 * @param byteBuffer
	 * @return 可序列化的对象
	 * @throws IOException
	 */
	public static Serializable readObject(ByteBuffer byteBuffer)
			throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		Serializable object = null;
		try {
			byte[] bytes = readToBytes(byteBuffer);
			// System.out.println(printByteArray(bytes));
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(new BufferedInputStream(bis));
			object = (Serializable) ois.readObject();
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
		return object;
	}

	/**
	 * 转化byte数组
	 *
	 * @param byteBuffer
	 * @return
	 */
	public static byte[] readToBytes(ByteBuffer byteBuffer) {
		byteBuffer.flip();
		// Retrieve bytes between the position and limit
		// (see Putting Bytes into a ByteBuffer)
		byte[] bytes = new byte[byteBuffer.remaining()];
		// transfer bytes from this buffer into the given destination array
		byteBuffer.get(bytes, 0, bytes.length);
		byteBuffer.clear();
		return bytes;
	}

	/**
	 * 聚集为一个ByteBuffer
	 *
	 * @param bytes
	 * @return
	 */
	public static ByteBuffer gather(List<ByteBuffer> bytes) {
		int length = 0;
		for (int i = 0; i < bytes.size(); i++) {
			bytes.get(i).flip();
			length += bytes.get(i).remaining();
		}
		ByteBuffer buffer = ByteBuffer.allocate(length);
		buffer.clear();
		for (int i = 0, offset = 0; i < bytes.size(); i++) {
			while (bytes.get(i).hasRemaining()) {
				buffer.put(bytes.get(i));
			}
			bytes.get(i).clear();
		}
		return buffer;
	}

	private static String printByteArray(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder(bytes.length);
		stringBuilder.append("length=").append(bytes.length).append("[");
		for (int i = 0; i < bytes.length; i++) {
			stringBuilder.append(bytes[i]);
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		ByteBuffer b1 = ByteBuffer.allocate(10);
		b1.put((byte) 1);
		b1.put((byte) 2);
		b1.put((byte) 3);
		ByteBuffer b2 = ByteBuffer.allocate(10);
		b2.put((byte) 1);
		b2.put((byte) 2);
		b2.put((byte) 3);
		ByteBuffer buffer = gather(Arrays.asList(b1, b2));
		byte[] bb = readToBytes(buffer);
		System.out.println(printByteArray(bb));
	}

}
