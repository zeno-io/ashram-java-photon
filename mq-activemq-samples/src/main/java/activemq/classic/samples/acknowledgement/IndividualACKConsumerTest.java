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

package activemq.classic.samples.acknowledgement;

import java.io.IOException;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

/**
 * 消费者：INDIVIDUAL_ACKNOWLEDGE
 *
 * @author Sven Augustus
 */
public class IndividualACKConsumerTest {

  //  TODO: INDIVIDUAL_ACKNOWLEDGE 单条消息确认, 区别于 CLIENT_ACKNOWLEDGE 是对拉取的最近消息一次性一批消息确认
  public static final int ACKNOWLEDGE_MODE = ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE;

  public static void main(String[] args) throws JMSException, IOException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");
    // 2、使用连接工厂创建一个连接对象
    Connection connection = connectionFactory.createConnection();
    connection.setClientID("myACKConnectionClient1");
    // 3、开启连接
    connection.start();
    // 4、使用连接对象创建会话（session）对象
    // FIXME ACK 模式
    Session session = connection.createSession(false, ACKNOWLEDGE_MODE);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    Topic topic = session.createTopic("test-topic-ack");
    // 6、使用会话对象创建消费者对象
    // FIXME 建立持久订阅的消费者
    MessageConsumer consumer = session.createDurableSubscriber(topic, "myACkConsumer1");
    // 7、主动拉取消息
    Message message = consumer.receive();
    if (message instanceof TextMessage) {
      TextMessage textMessage = (TextMessage) message;
      try {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(),
            textMessage.getText());
      } catch (JMSException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
//    Message message2 = consumer.receive();
//    if (message2 instanceof TextMessage) {
//      TextMessage textMessage = (TextMessage) message2;
//      try {
//        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(),
//            textMessage.getText());
//      } catch (JMSException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//      }
//    }
    // FIXME 消息确认
    //  如果不确认，再次消费会得到一样的消息
    message.acknowledge();

    // 8、关闭资源
    consumer.close();
    session.close();
    connection.close();
  }

}
