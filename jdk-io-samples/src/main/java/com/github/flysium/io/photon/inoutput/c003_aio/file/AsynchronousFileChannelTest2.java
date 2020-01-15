
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

package com.github.flysium.io.photon.inoutput.c003_aio.file;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 异步文件读写通道：AsynchronousFileChannel 读文件完整内容
 *
 * @author Sven Augustus
 */
public class AsynchronousFileChannelTest2 {

	public static void main(String[] args) {
		Path file = Paths.get(".", "file.bin");
		// System.out.println("文件路径:" + file.normalize().toAbsolutePath());

		AsynchronousFileChannel channel = null;
		try {
			channel = AsynchronousFileChannel
					.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ,
							StandardOpenOption.WRITE);
			// 写入
			ByteBuffer byteBuffer = ByteBuffer.allocate(10240);
			byteBuffer.clear();
			for (int i = 0; i < 2000; ++i) {
				byteBuffer.put(String.valueOf(i).getBytes("utf-8"));
				byteBuffer.put(String.valueOf(",").getBytes("utf-8"));
			}
			byteBuffer.flip();
			Future<Integer> integerFuture = channel.write(byteBuffer, 0);
			integerFuture.get();
			// 读文件完整内容
			readFully(channel);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	// 读文件完整内容
	private static void readFully(final AsynchronousFileChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1000);
		channel.read(buffer, 0, channel, new TryReadComplete(channel, buffer));
		// wait fur user to press a key otherwise java exits because the
		// asynch thread isn't important enough to keep it running.
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}

	public static class TryReadComplete implements
			CompletionHandler<Integer, AsynchronousFileChannel> {

		// the posistion of next part.
		private int pos = 0;
		private final AsynchronousFileChannel channel;
		private final ByteBuffer buffer;

		public TryReadComplete(AsynchronousFileChannel channel, ByteBuffer buffer) {
			this.channel = channel;
			this.buffer = buffer;
		}

		@Override
		public void completed(Integer result, AsynchronousFileChannel attachment) {
			if (result != -1) {
				pos += result;
				// handle data
				byte[] bb = ByteBufferUtils.readToBytes(buffer);
				System.out.println(new String(bb));

				buffer.clear();// reset the buffer so you can read next part.
			}
			// start next read operation
			attachment.read(buffer, pos, attachment, this);
		}

		@Override
		public void failed(Throwable exc, AsynchronousFileChannel attachment) {
			System.out.println("Error:" + exc.getCause());
			exc.printStackTrace();
			System.out.println("Close Channel");
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
