
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
