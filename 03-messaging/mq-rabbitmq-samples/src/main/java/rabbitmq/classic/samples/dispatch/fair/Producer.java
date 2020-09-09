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

package rabbitmq.classic.samples.dispatch.fair;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class Producer extends Declare {

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, InterruptedException {
    // 声明队列
    Channel channel = prepare();

    byte[] messageBodyBytes = ("Hello World! " + LocalDateTime.now()).getBytes();
    // 发送消息，注意如果发送失败，不会自动重发
    for (int i = 0; i < 50; i++) {
      String msg = String.valueOf((i + 1));
      // 这里仅做测试，不做持久化
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
      System.out.println("Send : " + msg);
      TimeUnit.MILLISECONDS.sleep(50);
    }

    channel.close();
    channel.getConnection().close();
  }

}
