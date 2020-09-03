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

package rabbitmq.classic.samples.acknowledgement;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 生产者：异步confirm模式
 *
 * @author Sven Augustus
 */
public class AsyncProducer {

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

    // TODO 异步confirm模式：提供一个回调方法，服务端confirm了一条或者多条消息后Client端会回调这个方法。
    final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
    channel.addConfirmListener(
        // RabbitMQ 返回 Basic.Ack
        new ConfirmCallback() {

          @Override
          public void handle(long deliveryTag, boolean multiple) throws IOException {
            // 每回调一次handleAck方法，unconfirm 集合删掉相应的一条（multiple=false）或多条（multiple=true）记录。
            System.out.println("Ack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
            if (multiple) {
              System.out.println("--multiple--");
              confirmSet.headSet(deliveryTag + 1).clear();
              // 用一个SortedSet, 返回此有序集合中小于end的所有元素。
            } else {
              System.out.println("--multiple false--");
              confirmSet.remove(deliveryTag);
            }
          }
        },
        // RabbitMQ 返回 Basic.Nack
        new ConfirmCallback() {
          @Override
          public void handle(long deliveryTag, boolean multiple) throws IOException {
            System.err.println("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
            if (multiple) {
              confirmSet.headSet(deliveryTag + 1).clear();
            } else {
              confirmSet.remove(deliveryTag);
            }
            // FIXME 注意这里需要添加处理消息重发的场景
          }
        });

    for (int i = 0; i < 3; i++) {
      byte[] messageBodyBytes = ("Hello World! " + LocalDateTime.now()).getBytes();
      // 发送消息
      long nextSeqNo = channel.getNextPublishSeqNo();
      AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
          // 消息持久化
          .deliveryMode(2).build();
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, properties, messageBodyBytes);
      confirmSet.add(nextSeqNo);
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
