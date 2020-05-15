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
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * 拉消费（Pull）, 获取单条消息
 *
 * @author Sven Augustus
 */
public class PullConsumer {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String QUEUE_NAME = "samples.queue";

  public static void main(String[] args)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 拉消费（Pull）, 基于 AMQP 协议的 basic.get 命令， 可以单条地获取消息
    // 消费者在订阅队列时，可以指定 autoAck 参数，当 autoAck 等于 false 时，
    //  RabbitMQ 会等待消费者显式地回复确认信号后才从内存(或者磁盘)中移去消息(实质上是先打上删除标记，之后再删除) 。
    //  当 autoAck 等于 true 时， RabbitMQ 会自动把发送出去的 消息置为确认，然后从内存(或者磁盘)中删除，而不管消费者是否真正地消费到了这些消息。
    GetResponse response = channel.basicGet(QUEUE_NAME, false);
    if (response != null) {
      byte[] body = response.getBody();
      Envelope envelope = response.getEnvelope();
      BasicProperties properties = response.getProps();
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
    } else {
      System.out.println("Receive No Messages.");
    }

    channel.close();
    conn.close();
  }

}
