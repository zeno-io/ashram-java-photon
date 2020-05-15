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

package activemq.classic.samples.retroactive;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

/**
 * Retroactive Consumer
 *
 * @author Sven Augustus
 */
public class RetroactiveConsumerTest {

  public static final String RETROACTIVE = "consumer.retroactive=true";

  public static void main(String[] args) throws JMSException, IOException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");
    // 2、使用连接工厂创建一个连接对象
    Connection connection = connectionFactory.createConnection();
    // 3、开启连接
    connection.start();
    // 4、使用连接对象创建会话（session）对象
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    // FIXME Retroactive Consumer 属于非持久订阅者
    //    1）ActiveMQ Broker可以为各种Topic缓存消息(但不支持 temporary topic 和 advisory topic)。这说明：该机制只针对Topic而言。
    //    2）缓存的消息只会发给 retroactive consumer，并不会发送给持久订阅者。
    //    Broke r的 activemq.xml 配置策略详情内容：subscriptionRecoveryPolicy
    Topic topic = new ActiveMQTopic("test-topic-retroactive" + "?" + RETROACTIVE);
//    Topic topic = session.createTopic("test-topic-retroactive" + "?" + RETROACTIVE);
    // 6、使用会话对象创建消费者对象
    // FIXME 特别注意：session.createConsumer(topic, messageSelector); 指定 messageSelector 针对 Retroactive Consumer 是无效，还可能导致无法消费
//    MessageConsumer consumer = session.createConsumer(topic, "myRetroactiveConsumer1");
    MessageConsumer consumer = session.createConsumer(topic);
    // 7、向consumer对象中设置一个messageListener对象，用来接收消息
    consumer.setMessageListener(new MessageListener() {

      @Override
      public void onMessage(Message message) {
        // TODO Auto-generated method stub
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
      }
    });
    try {
      TimeUnit.SECONDS.sleep(15);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // 8、关闭资源
    consumer.close();
    session.close();
    connection.close();
  }

}
