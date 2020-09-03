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

package xyz.flysium.photon.c020_networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * backlog 配置ServerSocket的最大客户端等待队列大小。
 * <p>
 * 这个参数设置为-1表示无限制，默认是50个最大等待队列。
 * <p>
 * ServerSocket有一个Linux分配的队列，存放还没有处理的客户端Socket，这个队列的容量就是backlog含义。
 * <p>
 * 如果队列已经被客户端socket占满了，如果还有新的连接过来，那么ServerSocket会拒绝新的连接。
 * <p>
 * 也就是说backlog提供了容量限制功能，避免太多的客户端socket占用太多服务器资源。
 *
 * <li>
 * 以backlog的代码为例，启动四个客户端连接服务端
 * <pre>
 *     netstat -tnap # 此时服务器已然产生 socket 监听状态，以及客户端的三次握手（数据包此时也能发送到服务器）, 但是只有 backlog+1 个连接 ESTABLISHED
 *     tcpdump port 9090 # 接收到客户端的三次握手数据包，如果客户端还发送数据，也接收到
 *     lsof -op 5006 # 5006 注意替换为Server的进程ID, 目前只有 LISTEN 的文件描述符，没有客户端相关连接的文件描述符
 *   </pre>
 * </li>
 * <li>
 * 当服务器开始accept后
 * <pre>
 *    netstat -tnap # 此时客户端的连接分配到进程 5006/java 了
 *     lsof -op 5006 # 此时多出了客户端相关连接的文件描述符 TCP node30:websm->10.0.2.3:51088 (ESTABLISHED)
 *    </pre>
 * </li>
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class T01_Server_backlog {

  public static void main(String[] args) throws IOException, InterruptedException {
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(9090), 2);
    // 测试用
    System.out.println("-----------> start to accept clients when you enter in console....");
    System.in.read();
    // 开始从服务器内存中接受客户端连接
    int acceptCount = 0;
    while (true) {
      Socket socket = serverSocket.accept();
      acceptCount++;
      System.out.println("receive from " + socket.getPort() + ", num=" + acceptCount);
      // 睡眠2秒，以更清晰观察
      TimeUnit.SECONDS.sleep(2);
    }
  }

}
