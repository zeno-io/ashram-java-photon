package com.github.flysium.io.photon.inoutput.c003_aio.file;

import com.github.flysium.io.photon.inoutput.c002_nio.ByteBufferUtils;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 异步文件读写通道：AsynchronousFileChannel 测试
 *
 * @author Sven Augustus
 */
public class AsynchronousFileChannelTest1 {

	public static void main(String[] args) {
		Path file = Paths.get(".", "file.bin");
		// System.out.println("文件路径:" + file.normalize().toAbsolutePath());

		AsynchronousFileChannel channel = null;
		try {
			channel = AsynchronousFileChannel
					.open(file, StandardOpenOption.CREATE, StandardOpenOption.READ,
							StandardOpenOption.WRITE);
			/*
			 * Becuase of the operations are synchronous. so the read maybe ended before
			 * write. You should test read/write just once everytime.
			 * 因为读写操作都是异步的，所以读操作也有可能在写操作前完成。 你应该每一次只测试其他一个IO操作，读或者写。
			 */
			// write(channel);
			read(channel);

			// wait fur user to press a key otherwise java exits because the
			// asynch thread isn't important enough to keep it running.
			try {
				System.in.read();
			} catch (IOException e) {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void close(AsynchronousFileChannel channel) {
		System.out.println("Close Channel");
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 读文件
	private static void read(final AsynchronousFileChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {// IO操作完成
				System.out.println("Bytes Read = " + result);
				Serializable data = null;
				try {
					data = ByteBufferUtils.readObject(attachment);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("读文件数据:" + data);
				close(channel);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {// IO操作失败
				System.out.println("Error:" + exc.getCause());
				exc.printStackTrace();
				close(channel);
			}
		});
	}

	// 写文件
	public static void write(final AsynchronousFileChannel channel) throws IOException {
		Serializable serializable = String.valueOf("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。\n");
		ByteBuffer byteBuffer = ByteBufferUtils.writeObject(serializable);
		channel.write(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {// IO操作完成
				System.out.println("Bytes write = " + result);
				close(channel);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {// IO操作失败
				System.out.println("Error:" + exc.getCause());
				exc.printStackTrace();
				close(channel);
			}
		});
	}

}
