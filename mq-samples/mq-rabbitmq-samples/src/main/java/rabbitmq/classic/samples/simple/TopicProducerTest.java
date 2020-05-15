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

package rabbitmq.classic.samples.simple;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * 生产者: Topic 模式交换器
 *
 * @author Sven Augustus
 */
public class TopicProducerTest {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String QUEUE_NAME = "samples.queue";
  private static final String EXCHANGE_NAME = "samples.exchange.topic";
  // 如果 topic 模式交换器， 路由key 是含有"."的字符串，会发送到含有“*”、“#”进行模糊匹配的 bingKey 对应的队列。
  private static final String BINDING_KEY = "samples.key.#";
  private static final String ROUTING_KEY = "samples.key.user.info";

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 创建了一个持久化的、非自动删除的、绑定类型为 topic 的交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    byte[] messageBodyBytes = "Hello Sven Augustus ! ".getBytes();
    // 发送消息
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, messageBodyBytes);
    System.out.println("Send OK. msg=" + new String(messageBodyBytes));

    channel.close();
    conn.close();
  }

}
