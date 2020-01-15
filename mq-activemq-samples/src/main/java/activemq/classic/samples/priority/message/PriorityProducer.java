
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

package activemq.classic.samples.priority.message;

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
public class PriorityProducer {

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
    Topic topic = session.createTopic("test-topic-priority");
    // 6、使用会话对象创建生产者对象
    MessageProducer producer = session.createProducer(topic);
    // 7、使用会话对象创建一个消息对象
    // FIXME 设置消息优先级
    //    JMS标准中约定priority可以为0~9的数值，值越大表示权重越高，默认值为4。
    //    不过activeMQ中各个存储器对priority的支持并非完全一样。比如JDBC存储器可以支持0~9，
    //    因为JDBC存储器可以基于priority对消息进行排序和索引化；
    //    但是对于kahadb/levelDB等这种基于日志文件的存储器而言，priority支持相对较弱，只能识别三种优先级(LOW: < 4,NORMAL: =4,HIGH: > 4)。
    //    在broker端，默认是不支持priority排序的，我们需要手动开启:
    //    <!-- 开启Topic的消息优先级排序 -->
    //    <policyEntry topic=">" prioritizedMessages="true"/>
    //    <!-- 开启Queue的消息优先级排序 -->
    //    <policyEntry queue=">" prioritizedMessages="true"/>
    TextMessage textMessage = createMessage(session, 4);
    producer.send(textMessage, DeliveryMode.PERSISTENT, 4, 0);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    textMessage = createMessage(session, 2);
    // FIXME 设置消息优先级
    producer.send(textMessage, DeliveryMode.PERSISTENT, 2, 0);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    textMessage = createMessage(session, 8);
    // FIXME 设置消息优先级
    producer.send(textMessage, DeliveryMode.PERSISTENT, 8, 0);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    // 9、关闭资源
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    producer.close();
    session.close();
    connection.close();
  }

  private static TextMessage createMessage(Session session, int priority) throws JMSException {
    return session
        .createTextMessage(
            "hello, test-topic-priority " + priority + " " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }

}
