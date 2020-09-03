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

package activemq.classic.samples.redelivery.deadletter;

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
import org.apache.activemq.RedeliveryPolicy;

/**
 * 消费者：发布订阅 Pub-Sub (Topic)
 *
 * @author Sven Augustus
 */
public class ConsumerToDLQTest {

  public static final int ACKNOWLEDGE_MODE = Session.CLIENT_ACKNOWLEDGE;

  public static void main(String[] args) throws JMSException, IOException {
    // 1、创建工厂连接对象，需要制定ip和端口号
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("mq", "mq123",
        "tcp://127.0.0.1:61616");

    // TODO 设置重投策略
    RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
    // 是否在每次尝试重新发送失败后,增长这个等待时间
    redeliveryPolicy.setUseExponentialBackOff(true);
    // TODO 重发次数,默认为6次   这里设置为 2 次
    redeliveryPolicy.setMaximumRedeliveries(2);
    // 重发时间间隔,默认为 1000毫秒（1秒）
    redeliveryPolicy.setInitialRedeliveryDelay(1000);
    // 第一次失败后重新发送之前等待500毫秒, 第二次失败再等待500 * 2毫秒, 这里的2就是value
    redeliveryPolicy.setBackOffMultiplier(2);
    // 是否避免消息碰撞
    redeliveryPolicy.setUseCollisionAvoidance(false);
    // 设置重发最大拖延时间-1 表示没有拖延， 只有UseExponentialBackOff(true)为true时生效
    // 假设首次重连间隔为10ms，倍数为2，那么第二次重连时间间隔为 20ms，
    //			第三次重连时间间隔为40ms，当重连时间间隔大的最大重连时间间隔时，
    //			以后每次重连时间间隔都为最大重连时间间隔。
    redeliveryPolicy.setMaximumRedeliveryDelay(-1);

    // 2、使用连接工厂创建一个连接对象
    Connection connection = connectionFactory.createConnection();
    connection.setClientID("myDlqConnectionClient1");

    // 3、开启连接
    connection.start();
    // 4、使用连接对象创建会话（session）对象
    Session session = connection.createSession(false, ACKNOWLEDGE_MODE);
    // 5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
    Topic topic = session.createTopic("test-topic-dlq");
    // 6、使用会话对象创建消费者对象
    //  建立持久订阅的消费者
    MessageConsumer consumer = session.createDurableSubscriber(topic, "myDlqConsumer1");
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

            //FIXME 触发 消息重新投递给消费者， 以便达到产生死信
            session.recover();
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
