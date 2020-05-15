/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rabbitmq.classic.samples.dispatch.fair;

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

    // TODO 设置 QoS 公平分发模式，消费者依据消费能力强弱与分发的消息成一定正比（需要同时设置 autoAck = false）
    channel.basicQos(1);
    // 推消费（Push）, 基于 AMQP 协议的 basic.consume 命令
    channel.basicConsume(QUEUE_NAME, false,
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
            // TODO 消费确认
            channel.basicAck(envelope.getDeliveryTag(), false);
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
