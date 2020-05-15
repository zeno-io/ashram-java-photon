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

package rabbitmq.classic.samples.expirations;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 生产者：消息的TTL
 *
 * @author Sven Augustus
 */
public class ProducerTestMessageTTL {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String QUEUE_NAME = "samples.ttl.queue";
  private static final String EXCHANGE_NAME = "samples.ttl.exchange.direct";
  // 如果 direct 模式交换器， ROUTING_KEY 与 BINDING_KEY 必须完全匹配
  private static final String BINDING_KEY = "samples.ttl.key";
  private static final String ROUTING_KEY = "samples.ttl.key";

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 创建了一个持久化的、非自动删除的、绑定类型为 direct 的交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    Map<String, Object> arguments = new HashMap<String, Object>(8);
    // FIXME 目前有两种方法可以设置消息的 TTL 。
    //  第一种方法是通过队列属性设置，队列中所有消息都有相同的过期时间。
    //  第二种方法是对消息本身进行单独设置，每条消息的 TTL 可以不同。
    //  FIXME 如果两种方法一起使用，则消息的 TTL 以两者之间较小的那个数值为准。
    arguments.put("x-message-ttl", 6000);
    // FIXME 注意：这里没有指定死信队列参数（x-dead-letter-exchange），当消息过期后，会被丢弃。
    // arguments.put("x-dead-letter-exchange", "dlx.exchange");
    channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);

    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    byte[] messageBodyBytes = ("Hello message ttl " + LocalDateTime.now()).getBytes();
    // 发送消息，注意如果发送失败，不会自动重发
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        // 消息持久化
        .deliveryMode(2)
        .contentEncoding("UTF-8")
        // FIXME 设置 TTL, 单位为毫秒
        .expiration("60000").build();
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, messageBodyBytes);
    System.out.println("Send maybe OK. msg=" + new String(messageBodyBytes));

    channel.close();
    conn.close();
  }

}
