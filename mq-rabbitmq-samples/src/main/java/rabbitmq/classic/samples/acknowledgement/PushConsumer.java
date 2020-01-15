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

package rabbitmq.classic.samples.acknowledgement;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 推消费（Push）
 *
 * @author Sven Augustus
 */
public class PushConsumer {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";
  private static final String QUEUE_NAME = "samples.queue";

  public static void main(String[] args)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 推消费（Push）, 基于 AMQP 协议的 basic.consume 命令
    channel.basicQos(64);
    channel.basicConsume(QUEUE_NAME, false,
        // 不同的订阅采用不同 消费者标签 (consumerTag) 来区分彼此 ，在同一个 Channel 中的消费者 需要通过唯一的消费者标签 作区分。
        "myPushConsumerTag1",
        new DefaultConsumer(channel) {

          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
              BasicProperties properties, byte[] body) throws IOException {
            long deliveryTag = envelope.getDeliveryTag();
            String exchange = envelope.getExchange();
            String routingKey = envelope.getRoutingKey();
            String contentType = properties.getContentType();
            // Decode message.
            String content = new String(body);

            System.out.printf("%s Receive New Messages, exchange= %s, routingKey= %s , body= %s %n",
                Thread.currentThread().getName(), exchange, routingKey, content);

            // TODO 消息确认
            //  RabbitMQ 会为未确认的消息设置过期时间，它判断此消息是否需要重新投递给消费者的。
            //  唯一依据是消费该消息的消费者连接是否己经断开，这么设计的原因是 RabbitMQ 允许消费者 消费一条消息的时间可以很久很久。
            channel.basicAck(deliveryTag, false);
            // TODO 消息拒绝，消息被拒绝 (Basic.Reject/Basic.Nack) ，井且设置 requeue 参数为 false, 会被进入死信队列（DLX）;
            // channel.basicReject(deliveryTag, true);
            // TODO 批量拒绝：multiple 参数设置为 true 则表示拒绝 deliveryTag 编号之前所有未被当前消费者确认的消息。
            // channel.basicNack(deliveryTag, true, true);
          }
        }
    );

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    channel.close();
    conn.close();
  }

}
