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

package activemq.classic.samples.simple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 生产者：发布订阅 Pub-Sub (Topic)
 *
 * @author Sven Augustus
 */
public class Producer {

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
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    Topic topic = session.createTopic("test-topic");
    // 6、使用会话对象创建生产者对象
    MessageProducer producer = session.createProducer(topic);
    // 7、使用会话对象创建一个消息对象
    TextMessage textMessage = session
        .createTextMessage("hello, test-topic " + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    // TODO 设置消息持久化, 默认是非持久化的
    textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
    // 8、发送消息
    // FIXME 如果是Topic, 发送的 Producer 在发送的时候没有还未曾有Consumer( 订阅者) 曾今订阅过此 Topic 。
    //   这个时候就不会有任何持久化。因为持久化一个没有订阅者的消息是浪费。
    // producer 默认是持久化模式
    producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    producer.send(textMessage);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());
    // 9、关闭资源
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    producer.close();
    session.close();
    connection.close();
  }

}
