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

/**
 * 消费者：CLIENT_ACKNOWLEDGE
 *
 * @author Sven Augustus
 */
public class ClientACKConsumerTest {

  //  TODO: CLIENT_ACKNOWLEDGE
  //      客户通过调用消息的acknowledge方法确认消息。需要注意的是，在这种模式中，确认是在会话层上进行，确认一个被消费的消息
  //      将自动确认所有已被会话消费的消息。例如，如果一个消息消费者消费了10 个消息，然后确认第5 个消息，那么所有10 个消息都被确认。
  public static final int ACKNOWLEDGE_MODE = Session.CLIENT_ACKNOWLEDGE;

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
