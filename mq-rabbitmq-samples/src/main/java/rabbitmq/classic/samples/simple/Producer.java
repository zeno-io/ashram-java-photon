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
import java.util.concurrent.TimeoutException;

/**
 * 生产者：direct 模式交换器
 *
 * @author Sven Augustus
 */
public class Producer {

  private static final String IP_ADDRESS = "127.0.0.1";
  private static final int PORT = 5672;
  private static final String USERNAME = "mq";
  private static final String PASSWORD = "mq123";
  private static final String VIRTUAL_HOST = "/";

  private static final String QUEUE_NAME = "samples.queue";
  private static final String EXCHANGE_NAME = "samples.exchange.direct";
  // 如果 direct 模式交换器， ROUTING_KEY 与 BINDING_KEY 必须完全匹配
  private static final String BINDING_KEY = "samples.key";
  private static final String ROUTING_KEY = "samples.key";

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(USERNAME);
    factory.setPassword(PASSWORD);
    factory.setVirtualHost(VIRTUAL_HOST);
    factory.setHost(IP_ADDRESS);
    factory.setPort(PORT);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    // 创建了一个持久化的、非自动删除的、绑定类型为 direct 的交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    byte[] messageBodyBytes = ("Hello World! " + LocalDateTime.now()).getBytes();
    // 发送消息，注意如果发送失败，不会自动重发
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        // 消息持久化
        .deliveryMode(2).build();
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, messageBodyBytes);
    System.out.println("Send maybe OK. msg=" + new String(messageBodyBytes));

    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    channel.close();
    conn.close();
  }

}
