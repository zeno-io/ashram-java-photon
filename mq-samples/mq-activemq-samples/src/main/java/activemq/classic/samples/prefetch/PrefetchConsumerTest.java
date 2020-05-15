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

package activemq.classic.samples.prefetch;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.apache.activemq.ActiveMQPrefetchPolicy;

/**
 * 消费者：发布订阅 Pub-Sub (Topic)
 *
 * @author Sven Augustus
 */
public class PrefetchConsumerTest {

  public static void main(String[] args) throws JMSException, IOException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");

    // FIXME 批量预取 prefetch 策略
    ActiveMQPrefetchPolicy prefetchPolicy = connectionFactory.getPrefetchPolicy();
    prefetchPolicy.setTopicPrefetch(100);
    prefetchPolicy.setQueuePrefetch(100);
    prefetchPolicy.setQueueBrowserPrefetch(100);
    // FIXME optimizeACK 优化选项（实际上默认情况下是开启的）
    connectionFactory.setOptimizeAcknowledge(true);
    // ack信息最大发送周期(毫秒)
    connectionFactory.setOptimizeAcknowledgeTimeOut(5000);

    // 2、使用连接工厂创建一个连接对象
    Connection connection = connectionFactory.createConnection();
    connection.setClientID("myPrefetchConnectionClient1");

    // 3、开启连接
    connection.start();
    // 4、使用连接对象创建会话（session）对象
    // FIXME 建议使用延迟确认, AUTO_ACKNOWLEDGE + optimizeACK 优化选项（指明AUTO_ACKNOWLEDGE采用“延迟确认”方式），重新设置prefetchSize数量为一个较小值
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    Topic topic = session.createTopic("test-topic-prefetch");
    // 6、使用会话对象创建消费者对象
    //  建立持久订阅的消费者
    MessageConsumer consumer = session.createDurableSubscriber(topic, "myPrefetchConsumer1");
    // 7、向consumer对象中设置一个messageListener对象，用来接收消息
    consumer.setMessageListener(new MessageListener() {

      @Override
      public void onMessage(Message message) {
        // TODO Auto-generated method stub
        if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          try {
            System.out.printf(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    + "%s Receive New Messages: %s %n", Thread.currentThread().getName(),
                textMessage.getText());
          } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    });
    // 8、程序等待接收用户消息
    try {
      TimeUnit.SECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // 9、关闭资源
    consumer.close();
    session.close();
    connection.close();
  }

}
