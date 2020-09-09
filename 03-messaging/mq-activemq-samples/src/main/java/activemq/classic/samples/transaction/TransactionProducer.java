
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

package activemq.classic.samples.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 生产者：事务
 *
 * @author Sven Augustus
 */
public class TransactionProducer {

  // TODO  先启动Consumer
  public static void main(String[] args) throws JMSException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");
    // 2、使用连接工厂创建一个连接对象
    Connection connection = connectionFactory.createConnection();
    // 3、开启连接
    connection.start();
    // 4、使用连接对象创建会话（session）对象
    // FIXME transacted = true 开启事务
    Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    Topic topic = session.createTopic("test-topic-trans");
    // 6、使用会话对象创建生产者对象
    MessageProducer producer = session.createProducer(topic);
    // 7、使用会话对象创建一个消息对象
    TextMessage textMessage = session
        .createTextMessage("hello, test-topic-trans " + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    // TODO 设置消息持久化, 默认是非持久化的
//    textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
    // TODO 特别注意，ActiveMQ 消息的 过期时间是基于时间戳的。
//    textMessage.setJMSExpiration(System.currentTimeMillis() + 5000);
    // 8、发送消息
    // FIXME 如果是Topic, 发送的 Producer 在发送的时候没有还未曾有Consumer( 订阅者) 曾今订阅过此 Topic 。
    //   这个时候就不会有任何持久化。因为持久化一个没有订阅者的消息是浪费。
    // producer 默认是持久化模式
//    producer.setDeliveryMode(DeliveryMode.PERSISTENT);

    try {
      producer.send(textMessage);
      // FIXME 事务提交
      //  如果不提交，这个消息是不会提交到 Broker
      session.commit();
      System.out.println("Send maybe OK. msg=" + textMessage.getText());
    } catch (Exception e) {
      session.rollback();
      // TODO 考虑消息重发
    }
    // 9、关闭资源
    //  如果是Servlet 容器，建议使用 shutdownHook 钩子
    producer.close();
    session.close();
    connection.close();
  }

}
