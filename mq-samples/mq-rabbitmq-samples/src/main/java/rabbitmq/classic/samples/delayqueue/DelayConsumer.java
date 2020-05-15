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

package rabbitmq.classic.samples.delayqueue;

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
 * 消费者：延迟队列
 *
 * @author Sven Augustus
 */
public class DelayConsumer {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  public static void main(String[] args)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 推消费（Push）, 基于 AMQP 协议的 basic.consume 命令
    consume(channel, "queue_delay_5s", "pushConsumerTag5s");
    consume(channel, "queue_delay_10s", "pushConsumerTag10s");
    consume(channel, "queue_delay_30s", "pushConsumerTag30s");
    consume(channel, "queue_delay_1min", "pushConsumerTag1min");

    try {
      TimeUnit.MINUTES.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    channel.close();
    conn.close();
  }

  private static void consume(Channel channel, String queueName, String pushConsumerTag1)
      throws IOException {
    channel.basicConsume(queueName, true,
        // 不同的订阅采用不同 消费者标签 (consumerTag) 来区分彼此 ，在同一个 Channel 中的消费者 需要通过唯一的消费者标签 作区分。
        pushConsumerTag1,
        new DefaultConsumer(channel) {

          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
              BasicProperties properties, byte[] body) {
            long deliveryTag = envelope.getDeliveryTag();
            String exchange = envelope.getExchange();
            String routingKey = envelope.getRoutingKey();
            String contentType = properties.getContentType();
            // Decode message.
            String content = new String(body);

            System.out.printf("%s Receive New Messages, exchange= %s, routingKey= %s , body= %s %n",
                Thread.currentThread().getName(), exchange, routingKey, content);
          }
        }
    );
  }

}
