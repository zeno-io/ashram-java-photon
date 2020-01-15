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
 * 生产者：批量同步ACK
 *
 * @author Sven Augustus
 */
public class BatchProducer {

  public static final String AMQP_URL = "amqp://mq:mq123@127.0.0.1:5672";

  private static final String QUEUE_NAME = "samples.queue.test";
  private static final String EXCHANGE_NAME = "samples.exchange.test";
  // 如果 direct 模式交换器， ROUTING_KEY 与 BINDING_KEY 必须完全匹配
  private static final String BINDING_KEY = "samples.test.key";
  private static final String ROUTING_KEY = "samples.test.key";

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
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);

    // 使用路由键将队列和交换器绑定起来
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY);

    // TODO 生产者通过调用channel的 confirmSelect 方法将channel设置为 confirm 模式
    channel.confirmSelect();

    for (int i = 0; i < 10; i++) {
      byte[] messageBodyBytes = ("Hello World! " + LocalDateTime.now()).getBytes();
      // 发送消息
      AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
          // 消息持久化
          .deliveryMode(2).build();
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, messageBodyBytes);
      System.out.println("Sending.... msg=" + new String(messageBodyBytes));
    }

    boolean sendOk = false;
    try {
      // TODO 批量confirm模式：每发送一批消息后，调用 waitForConfirms()方法 (方法与普通confirm模式一样，区别只在于前面发送了一个消息还是一批消息)，等待服务器端confirm。批量confirm模式稍微复杂一点，客户端程序需要定期（每隔多少秒）或者定量（达到多少条）或者两则结合起来publish消息，
      //  然后等待服务器端confirm,相比普通confirm模式，批量极大提升confirm效率。
      //  但是问题在于一旦出现confirm返回false或者超时的情况时，客户端需要将这一批次的消息全部重发，这会带来明显的重复消息数量，
      //  并且，当消息经常丢失时，批量confirm性能应该是不升反降的。
      sendOk = channel.waitForConfirms();
    } catch (InterruptedException e) {
      sendOk = false;
    } finally {
      if (sendOk) {
        System.out.println("Send OK.");
      } else {
        System.err.println("Send Failed.");
        // FIXME 注意这里需要添加处理消息重发的场景
      }
    }

    channel.close();
    conn.close();
  }

}
