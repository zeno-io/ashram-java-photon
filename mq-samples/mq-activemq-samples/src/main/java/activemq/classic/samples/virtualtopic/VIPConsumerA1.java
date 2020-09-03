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

package activemq.classic.samples.virtualtopic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者：发布订阅 Pub-Sub (Topic)
 *
 * @author Sven Augustus
 */
public class VIPConsumerA1 {

  public static final String CONSUMER = "Consumer.";
  public static final String VIRTUAL_TOPIC = "VirtualTopic.";

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
    // FIXME 消费者使用 Queue, 命名以  Consumer. 为前缀，VirtualTopic. 为中缀，如 Consumer.A.VirtualTopic.test2, A为消费方的系统名称。test2为根据业务定义的消息地址
    Queue queue = session.createQueue(CONSUMER + "A"+"." + VIRTUAL_TOPIC + "test2");
    // 6、使用会话对象创建消费者对象
    MessageConsumer consumer = session.createConsumer(queue);
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
