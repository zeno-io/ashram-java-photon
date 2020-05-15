package com.github.flysium.io.photon.inoutput.c002_nio.s02_file_channel.lock;

/**
 * 阻塞写
 *
 * @author Sven Augustus
 */
public class FileLockMain_w extends FileChannelTest2 {

	public static void main(String[] args) throws InterruptedException {
		final String path = "file.txt";
		// 阻塞写
		write(path, "线程B");
	}

}
