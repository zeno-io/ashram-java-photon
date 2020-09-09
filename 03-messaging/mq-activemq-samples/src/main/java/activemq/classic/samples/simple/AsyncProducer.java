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

package activemq.classic.samples.simple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.jms.Connection;
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
public class AsyncProducer {

  // TODO  先启动Consumer
  public static void main(String[] args) throws JMSException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");
    // FIXME 非持久化消息默认采用异步发送，持久化消息默认采用同步发送，这里也可以指定
    connectionFactory.setUseAsyncSend(true);
    // FIXME 同样设置消息发送者在累计发送102400byte大小的消息后
    //  等待服务端进行回执,以便确定之前发送的消息是否被正确处理
    //  确定服务器端是否产生了过量的消息堆积，需要减慢消息生产端的生产速度
    connectionFactory.setProducerWindowSize(102400);

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
        .createTextMessage("hello, test-topic async " + LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    //  设置消息持久化, 默认是非持久化的
    textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
    // 8、发送消息
    // producer 默认是持久化模式
    producer.send(textMessage);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());
    // 9、关闭资源
    //  如果是Servlet 容器，建议使用 shutdownHook 钩子
    producer.close();
    session.close();
    connection.close();
  }

}
