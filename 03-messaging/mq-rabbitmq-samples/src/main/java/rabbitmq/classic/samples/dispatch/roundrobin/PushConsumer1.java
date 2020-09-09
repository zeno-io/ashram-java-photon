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

package rabbitmq.classic.samples.dispatch.roundrobin;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @author Sven Augustus
 */
public class PushConsumer1 extends Declare {

  public static void main(String[] args)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {
    // 声明队列
    Channel channel = prepare();

    // 推消费（Push）, 基于 AMQP 协议的 basic.consume 命令
    channel.basicConsume(QUEUE_NAME, true,
        // 不同的订阅采用不同 消费者标签 (consumerTag) 来区分彼此 ，在同一个 Channel 中的消费者 需要通过唯一的消费者标签 作区分。
        "myPushConsumerTag1",
        new DefaultConsumer(channel) {

          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
              BasicProperties properties, byte[] body) throws IOException {
            // Decode message.
            String content = new String(body);

            System.out.printf("Receive New Messages,  msg= %s %n", content);

            // FIXME 这里睡眠100毫秒，模拟 myPushConsumerTag1 的消费能力较弱
            try {
              TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
    );

    try {
      TimeUnit.SECONDS.sleep(30);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    channel.close();
    channel.getConnection().close();
  }

}
