/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.inoutput.c002_nio.s02_file_channel.lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * FileChannel文件锁示例
 *
 * @author Sven Augustus
 * @version 2017年2月8日
 */
public class FileChannelTest2 {

	// 阻塞读，共享锁模式
	protected static void read(String path, final String flag) {
		System.out.println(flag + " " + new Date().toLocaleString() + "准备读文件");
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		java.nio.channels.FileChannel channel = null;
		FileLock lock = null;
		String lockDesc = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			channel = fis.getChannel();
			// lock = channel.lock();// 独占锁
			// 对于一个只读channel通过任意方式加锁时会报NonWritableChannelException异常
			lock = channel.lock(0L, Long.MAX_VALUE, true);// 有参lock()为共享锁，有写操作会报异常
			lockDesc = (lock.isShared() ? "共享锁" : "独占锁");
			System.out
					.println(flag + " " + new Date().toLocaleString() + "获取文件" + (lockDesc) + lock);

			// 读取buffer
			buffer.rewind();
			while (channel.read(buffer) > 0) {
				// 读取buffer
				buffer.flip();
				Charset charset = Charset.defaultCharset();
				CharBuffer charBuffer = charset.decode(buffer);
				// System.out.print(charBuffer);
			}

			// 睡眠5秒，独占文件锁更长一些时间
			Thread.sleep(5000);
		} catch (Exception e) {
			System.err.println(flag + " " + e.toString());
		} finally {
			try {
				if (lock != null) {
					lock.release();
					System.out.println(
							flag + " " + new Date().toLocaleString() + "释放文件" + (lockDesc));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(flag + " " + new Date().toLocaleString() + "结束读文件");
	}

	// 非阻塞读，共享锁模式
	protected static void readTry(String path, final String flag) {
		System.out.println(flag + " " + new Date().toLocaleString() + "准备读文件");
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		java.nio.channels.FileChannel channel = null;
		FileLock lock = null;
		String lockDesc = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			channel = fis.getChannel();
			// lock = channel.lock();// 独占锁
			// 对于一个只读channel通过任意方式加锁时会报NonWritableChannelException异常
			lock = channel.tryLock(0L, Long.MAX_VALUE, true);// 有参lock()为共享锁，有写操作会报异常
			lockDesc = (lock.isShared() ? "共享锁" : "独占锁");
			System.out
					.println(flag + " " + new Date().toLocaleString() + "获取文件" + (lockDesc) + lock);

			// 读取buffer
			buffer.rewind();
			while (channel.read(buffer) > 0) {
				// 读取buffer
				buffer.flip();
				Charset charset = Charset.defaultCharset();
				CharBuffer charBuffer = charset.decode(buffer);
				// System.out.print(charBuffer);
			}

			// 睡眠5秒，独占文件锁更长一些时间
			Thread.sleep(5000);
		} catch (Exception e) {
			System.err.println(flag + " " + e.toString());
		} finally {
			try {
				if (lock != null) {
					lock.release();
					System.out.println(
							flag + " " + new Date().toLocaleString() + "释放文件" + (lockDesc));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(flag + " " + new Date().toLocaleString() + "结束读文件");
	}

	// 阻塞写
	protected static void write(String path, final String flag) {
		System.out.println(flag + " " + new Date().toLocaleString() + "准备写文件");
		StringBuilder s = new StringBuilder("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n");
		s.append(new Date().toLocaleString());
		ByteBuffer buffer = ByteBuffer.wrap(s.toString().getBytes());

		java.nio.channels.FileChannel channel = null;
		FileLock lock = null;
		String lockDesc = "";
		try {
			FileOutputStream fos = new FileOutputStream(path);
			channel = fos.getChannel();
			lock = channel.lock();// 独占锁
			lockDesc = (lock.isShared() ? "共享锁" : "独占锁");
			System.out
					.println(flag + " " + new Date().toLocaleString() + "获取文件" + (lockDesc) + lock);

			// 从buffer读取数据写入channel
			buffer.rewind();
			channel.write(buffer);

			// 睡眠5秒，独占文件锁更长一些时间
			Thread.sleep(5000);
		} catch (Exception e) {
			System.err.println(flag + " " + e.toString());
		} finally {
			try {
				if (lock != null) {
					lock.release();
					System.out.println(
							flag + " " + new Date().toLocaleString() + "释放文件" + (lockDesc));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(flag + " " + new Date().toLocaleString() + "结束写文件");
	}

	// 阻塞写，共享锁模式
	protected static void writeByShared(String path, final String flag) {
		System.out.println(flag + " " + new Date().toLocaleString() + "准备写文件");
		StringBuilder s = new StringBuilder("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n");
		s.append(new Date().toLocaleString());
		ByteBuffer buffer = ByteBuffer.wrap(s.toString().getBytes());

		java.nio.channels.FileChannel channel = null;
		FileLock lock = null;
		String lockDesc = "";
		try {
			File file = new File(path);
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			channel = raf.getChannel();
			lock = channel.lock(0L, Long.MAX_VALUE, true);// 有参lock()为共享锁，有写操作会报异常
			lockDesc = (lock.isShared() ? "共享锁" : "独占锁");
			System.out
					.println(flag + " " + new Date().toLocaleString() + "获取文件" + (lockDesc) + lock);

			channel.position(channel.size()); // 定位到文件末尾
			// 强制刷出到内存
			channel.force(true);

			// 从buffer读取数据写入channel
			buffer.rewind();
			channel.write(buffer);

			// 睡眠5秒，独占文件锁更长一些时间
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(flag + " " + e.toString());
		} finally {
			try {
				if (lock != null) {
					lock.release();
					System.out.println(
							flag + " " + new Date().toLocaleString() + "释放文件" + (lockDesc));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(flag + " " + new Date().toLocaleString() + "结束写文件");
	}

	// 非阻塞写
	protected static void writeTry(String path, final String flag) {
		System.out.println(flag + " " + new Date().toLocaleString() + "准备写文件");
		StringBuilder s = new StringBuilder("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n");
		s.append(new Date().toLocaleString());
		ByteBuffer buffer = ByteBuffer.wrap(s.toString().getBytes());

		java.nio.channels.FileChannel channel = null;
		FileLock lock = null;
		String lockDesc = "";
		try {
			FileOutputStream fos = new FileOutputStream(path);
			channel = fos.getChannel();
			lock = channel.tryLock();// 独占锁
			lockDesc = (lock.isShared() ? "共享锁" : "独占锁");
			System.out
					.println(flag + " " + new Date().toLocaleString() + "获取文件" + (lockDesc) + lock);

			// 从buffer读取数据写入channel
			buffer.rewind();
			channel.write(buffer);

			// 睡眠5秒，独占文件锁更长一些时间
			Thread.sleep(5000);
		} catch (Exception e) {
			System.err.println(flag + " " + e.toString());
		} finally {
			try {
				if (lock != null) {
					lock.release();
					System.out.println(
							flag + " " + new Date().toLocaleString() + "释放文件" + (lockDesc));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (channel != null) {
					channel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(flag + " " + new Date().toLocaleString() + "结束写文件");
	}

}
