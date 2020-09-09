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

package rabbitmq.classic.samples.deadletter;

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
 * 生产者：死信队列
 *
 * @author Sven Augustus
 */
public class ProducerTestDLX {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String DLX_QUEUE_NAME = "samples.dlx.test";
  private static final String DLX_EXCHANGE_NAME = "samples.dlx.exchange";
  private static final String DLX_BINDING_KEY = "#";

  private static final String QUEUE_NAME = "samples.dlx.normal";
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

    // 创建了一个持久化的、非自动删除的、绑定类型为 topic 的交换器
    channel.exchangeDeclare(DLX_EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    channel.queueDeclare(DLX_QUEUE_NAME, true, false, false, null);
    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(DLX_QUEUE_NAME, DLX_EXCHANGE_NAME, DLX_BINDING_KEY);

    // 创建了一个持久化的、非自动删除的、绑定类型为 direct 的交换器
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false,
        new HashMap<>(8));
    // 队列被声明为持久化的 非排他的 非自动删除的，而且也被分配另一个确定的己知的名称(由客户端分配而非 RabbitMQ 自动生成)。
    Map<String, Object> arguments = new HashMap<String, Object>(8);
    // FIXME 指定死信队列参数（x-dead-letter-exchange）
    arguments.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
    // FIXME 指定死信队列参数（x-dead-letter-routing-key） 指定路由键，如果没有特殊指定，则使用原队列的路由键
    arguments.put("x-dead-letter-routing-key", "anything.now");
    channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    byte[] messageBodyBytes = ("Hello message dlx " + LocalDateTime.now()).getBytes();
    // 发送消息，注意如果发送失败，不会自动重发
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
        // 消息持久化
        .deliveryMode(2)
        .contentEncoding("UTF-8")
        // FIXME 设置 TTL, 单位为毫秒
        .expiration("6000").build();
    channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, messageBodyBytes);
    System.out.println("Send maybe OK. msg=" + new String(messageBodyBytes));

    channel.close();
    conn.close();
  }

}
