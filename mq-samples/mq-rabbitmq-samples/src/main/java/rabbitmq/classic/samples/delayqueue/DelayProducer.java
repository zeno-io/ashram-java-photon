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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 生产者：延迟队列 TTL+DLX
 *
 * @author Sven Augustus
 */
public class DelayProducer {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(AMQP_URL);
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();

    declareDelayQueue(channel, "queue_delay_5s",
        "queue_exchange_delay_5s",
        "queue_5s", "queue_exchange_5s",
        "5s");
    declareDelayQueue(channel, "queue_delay_10s",
        "queue_exchange_delay_10s",
        "queue_10s", "queue_exchange_10s",
        "10s");
    declareDelayQueue(channel, "queue_delay_30s",
        "queue_exchange_delay_30s",
        "queue_30s", "queue_exchange_30s",
        "30s");
    declareDelayQueue(channel, "queue_delay_1min",
        "queue_exchange_delay_1min",
        "queue_1min", "queue_exchange_1min",
        "1min");

    channel.confirmSelect();

    // 发送消息，注意如果发送失败，不会自动重发
    publishAndConfirms(channel, "queue_exchange_5s", "5s", 5 * 1000,
        ("message 5s delay.").getBytes());
    publishAndConfirms(channel, "queue_exchange_10s", "10s", 10 * 1000,
        ("message 10s delay.").getBytes());
    publishAndConfirms(channel, "queue_exchange_30s", "30s", 30 * 1000,
        ("message 30s delay.").getBytes());
    publishAndConfirms(channel, "queue_exchange_1min", "1min", 60 * 1000,
        ("message 60s delay.").getBytes());

    channel.close();
    conn.close();
  }

  private static void declareDelayQueue(Channel channel,
      String delayQueueName, String delayExchangeName,
      String queueName, String exchangeName, String bindingKey)
      throws IOException {
    // 创建了一个持久化的、非自动删除的、绑定类型为 topic 的交换器
    channel.exchangeDeclare(delayExchangeName, BuiltinExchangeType.TOPIC, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    channel.queueDeclare(delayQueueName, true, false, false, null);
    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(delayQueueName, delayExchangeName, bindingKey);

    // 创建了一个持久化的、非自动删除的、绑定类型为 direct 的交换器
    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    Map<String, Object> arguments = new HashMap<String, Object>(8);
    // FIXME 指定死信队列参数（x-dead-letter-exchange）
    arguments.put("x-dead-letter-exchange", delayExchangeName);
    channel.queueDeclare(queueName, true, false, false, arguments);
    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(queueName, exchangeName, bindingKey);
  }

  private static void publishAndConfirms(Channel channel, String exchangeName, String routingKey,
      long expiration,
      byte[] messageBodyBytes)
      throws IOException {
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        // 消息持久化
        .deliveryMode(2)
        .contentEncoding("UTF-8")
        // FIXME 设置 TTL, 单位为毫秒
        .expiration(String.valueOf(expiration)).build();
    channel.basicPublish(exchangeName, routingKey, properties, messageBodyBytes);
    boolean sendOk = false;
    try {
      //  普通confirm模式：每发送一条消息后，调用waitForConfirms()方法，等待服务器端confirm。实际上是一种串行confirm了。
      //  其返回的条件是客户端收到了相应的 Basic.Ack/.Nack 或者被中断。
      sendOk = channel.waitForConfirms();
    } catch (InterruptedException e) {
      sendOk = false;
    } finally {
      if (sendOk) {
        System.out.println("Send OK. msg=" + new String(messageBodyBytes));
      } else {
        System.err.println("Send Failed.");
        //  注意这里需要添加处理消息重发的场景
      }
    }
  }

}
