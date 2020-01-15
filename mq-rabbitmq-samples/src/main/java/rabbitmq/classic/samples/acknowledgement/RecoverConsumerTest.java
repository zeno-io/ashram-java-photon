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

import com.rabbitmq.client.AMQP.Basic.RecoverOk;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 拉消费（Pull）, 补发消息
 *
 * @author Sven Augustus
 */
public class RecoverConsumerTest {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String QUEUE_NAME = "samples.queue.test";

  public static void main(String[] args)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

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

      // TODO 发现消息是有问题，请求补发
      //  basicRecover 用来请求 RabbitMQ 重新发送还未被确认的消息
      //    requeue 参数设置为 true ，则未被确认的消息会被重新加入到队列中，这样对于同一条消息来说，可能会被分配给与之前不同的消费者。
      //    如果 requeue 参数设置为 false ，那么同一条消息会被分配给与之前相同的消费者。( RabbitMQ reply-code=540, reply-text=NOT_IMPLEMENTED )
      //    默认情况下，如果不设置 requeue 这个参数，相当于 channel.basicRecover(true) ，即 requeue 默认为 true
      RecoverOk recoverOk = channel.basicRecover(true);
      System.out.println("Recover: " + recoverOk);

      GetResponse response2 = channel.basicGet(QUEUE_NAME, false);
      if (response2 != null) {
        byte[] body2 = response2.getBody();
        Envelope envelope2 = response2.getEnvelope();
        BasicProperties properties2 = response2.getProps();
        long deliveryTag2 = envelope2.getDeliveryTag();
        String exchange2 = envelope2.getExchange();
        String routingKey2 = envelope2.getRoutingKey();
        String contentType2 = properties2.getContentType();
        // Decode message.
        String content2 = new String(body2);
        System.out.printf("%s Receive Messages Again, exchange= %s, routingKey= %s , body= %s %n",
            Thread.currentThread().getName(), exchange2, routingKey2, content2);

        // TODO 消息确认
        //  RabbitMQ 会为未确认的消息设置过期时间，它判断此消息是否需要重新投递给消费者的。
        //  唯一依据是消费该消息的消费者连接是否己经断开，这么设计的原因是 RabbitMQ 允许消费者 消费一条消息的时间可以很久很久。
        channel.basicAck(deliveryTag2, false);
      } else {
        System.out.println("Receive No Messages.");
      }
    } else {
      System.out.println("Receive No Messages.");
    }

    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    channel.close();
    conn.close();
  }

}
