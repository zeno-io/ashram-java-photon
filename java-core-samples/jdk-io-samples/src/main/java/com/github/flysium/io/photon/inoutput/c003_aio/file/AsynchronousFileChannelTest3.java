
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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步文件读写通道：AsynchronousFileChannel ExecutorService\FileAttribute构造参数测试
 *
 * @author Sven Augustus
 */
public class AsynchronousFileChannelTest3 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>()) {

			@Override
			public void execute(Runnable command) {
				super.execute(command);
				System.out.println("Thread-name=" + command.toString());
			}
		};

		AsynchronousFileChannel channel1 = null;
		AsynchronousFileChannel channel2 = null;
		try {
			Path file1 = Paths.get(".", "file3-1.bin");
			FileAttribute[] attrs1 = new FileAttribute[]{
					// windows 目前只支持修改acl:acl属性
					// at
					// sun.nio.fs.WindowsSecurityDescriptor.fromAttribute(WindowsSecurityDescriptor.java:355)
					// if(!var5.name().equals("acl:acl")) {
					// throw new UnsupportedOperationException("'" + var5.name() + "' not supported
					// as initial attribute");
					// }
					newFileAttributeACL(file1)};
			// System.out.println("文件路径:" + file.normalize().toAbsolutePath());
			channel1 = AsynchronousFileChannel.open(file1,
					EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.READ,
							StandardOpenOption.WRITE),
					threadPoolExecutor, attrs1);
			channel1.write(ByteBuffer.wrap("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。".getBytes()), 0);

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			channel1.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					attachment.flip();
					// System.out.println(Charset.forName("utf-8").decode(attachment));
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
				}
			});

			Path file2 = Paths.get(".", "file3-2.bin");
			// System.out.println("文件路径:" + file.normalize().toAbsolutePath());
			channel2 = AsynchronousFileChannel.open(file2,
					EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.READ,
							StandardOpenOption.WRITE),
					threadPoolExecutor);
			channel2.write(ByteBuffer.wrap("赵客缦胡缨，吴钩霜雪明。银鞍照白马，飒沓如流星。".getBytes()), 0);

			ByteBuffer buffer2 = ByteBuffer.allocate(1024);
			channel2.read(buffer2, 0, buffer2, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					attachment.flip();
					// System.out.println(Charset.forName("utf-8").decode(attachment));
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
				}
			});

			TimeUnit.SECONDS.sleep(1);
			while (threadPoolExecutor.getActiveCount() > 0) {
				TimeUnit.MILLISECONDS.sleep(10);
			}
			System.out.println("已完成的线程数===" + threadPoolExecutor.getCompletedTaskCount());
			printFileAttr(file1);

			// wait fur user to press a key otherwise java exits because the
			// asynch thread isn't important enough to keep it running.
			try {
				System.in.read();
			} catch (IOException e) {
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\nClose Channel");
			if (channel1 != null) {
				try {
					channel1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (channel2 != null) {
				try {
					channel2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static FileAttribute newFileAttributeACL(Path file) throws IOException {
		// lookup "svenfire"
		UserPrincipal userPrincipal = file.getFileSystem().getUserPrincipalLookupService()
				.lookupPrincipalByName("svenfire");

		// get view
		AclFileAttributeView view = Files.getFileAttributeView(file, AclFileAttributeView.class);

		// create ACE to give "svenfire" read/write/delete access
		AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW)
				.setPrincipal(userPrincipal)
				.setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ATTRIBUTES,
						AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ATTRIBUTES,
						AclEntryPermission.DELETE)
				.build();

		// read ACL, insert ACE, re-write ACL
		final List<AclEntry> acl = new ArrayList<AclEntry>(); // view.getAcl();
		acl.add(0, entry); // insert before any DENY entries
		// view.setAcl(acl);

		return new FileAttribute() {
			@Override
			public String name() {
				return "acl:acl";
			}

			@Override
			public Object value() {
				return acl;
			}
		};
	}

	private static void printFileAttr(Path file) {
		try {
			// BasicFileAttributes ra = Files.readAttributes(file,
			// BasicFileAttributes.class);
			// System.out.println(ra);
			System.out.println("UserDefinedAttributes:");
			UserDefinedFileAttributeView udfav = Files
					.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			for (String name : udfav.list()) {
				System.out.println(udfav.size(name) + "" + name);
			}
			System.out.println("AclFileAttributes:");
			AclFileAttributeView view = Files
					.getFileAttributeView(file, AclFileAttributeView.class);
			for (AclEntry aclEntry : view.getAcl()) {
				System.out.println(aclEntry.toString());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
