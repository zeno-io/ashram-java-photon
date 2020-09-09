
/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.c003_aio.file;

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
      FileAttribute<List<AclEntry>> attrs1 = newFileAttributeACL(file1);
      // windows 目前只支持修改acl:acl属性
      // at
      // sun.nio.fs.WindowsSecurityDescriptor.fromAttribute(WindowsSecurityDescriptor.java:355)
      // if(!var5.name().equals("acl:acl")) {
      // throw new UnsupportedOperationException("'" + var5.name() + "' not supported
      // as initial attribute");
      // }
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
      // async thread isn't important enough to keep it running.
      try {
        System.in.read();
      } catch (IOException ignored) {
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

  private static FileAttribute<List<AclEntry>> newFileAttributeACL(Path file) throws IOException {
    // lookup "svenaugustus"
    UserPrincipal userPrincipal = file.getFileSystem().getUserPrincipalLookupService()
        .lookupPrincipalByName("svenaugustus");

    // get view
    AclFileAttributeView view = Files.getFileAttributeView(file, AclFileAttributeView.class);

    // create ACE to give "svenaugustus" read/write/delete access
    AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW)
        .setPrincipal(userPrincipal)
        .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ATTRIBUTES,
            AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ATTRIBUTES,
            AclEntryPermission.DELETE)
        .build();

    // read ACL, insert ACE, re-write ACL
    final List<AclEntry> acl = new ArrayList<AclEntry>(); // view.getAcl();
    // insert before any DENY entries
    acl.add(0, entry);
    // view.setAcl(acl);

    return new FileAttribute<List<AclEntry>>() {
      @Override
      public String name() {
        return "acl:acl";
      }

      @Override
      public List<AclEntry> value() {
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
      UserDefinedFileAttributeView uaw = Files
          .getFileAttributeView(file, UserDefinedFileAttributeView.class);
      for (String name : uaw.list()) {
        System.out.println(uaw.size(name) + "" + name);
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
