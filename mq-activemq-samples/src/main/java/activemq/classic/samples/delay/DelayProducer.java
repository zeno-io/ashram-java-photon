
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

package activemq.classic.samples.delay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

/**
 * 生产者：发布订阅 Pub-Sub (Topic)
 *
 * @author Sven Augustus
 */
public class DelayProducer {

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
    Topic topic = session.createTopic("test-topic-delay");
    // 6、使用会话对象创建生产者对象
    MessageProducer producer = session.createProducer(topic);
    // 7、使用会话对象创建一个消息对象
    TextMessage textMessage = session
        .createTextMessage(
            "hello, test-topic-delay 5 " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    // FIXME AMQ不支持 setJMSDeliveryTime
    // FIXME 设置消息延迟时间， 单位毫秒
    //  首先Broker 配置 <broker xmlns="http://activemq.apache.org/schema/core" schedulerSupport="true" > </broker>
    //  延时消息属性总结：
    //  AMQ_SCHEDULED_DELAY 	long 	The time in milliseconds that a message will wait before being scheduled to be delivered by the broker
    //  AMQ_SCHEDULED_PERIOD 	long 	The time in milliseconds to wait after the start time to wait before scheduling the message again
    //  AMQ_SCHEDULED_REPEAT 	int 	The number of times to repeat scheduling a message for delivery
    //  AMQ_SCHEDULED_CRON 	String 	Use a Cron entry to set the schedule

    // 以下是延迟5秒
    textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5000);
    producer.send(textMessage);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    // 以下是 wait with an initial delay, and the repeat delivery 10 times, waiting 10 seconds between each re-delivery
    // 是一个重发投递10次 （AMQ_SCHEDULED_REPEAT），每次等10秒（AMQ_SCHEDULED_PERIOD）
    textMessage = session
        .createTextMessage(
            "hello, test-topic-delay 5-10-10 " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5000);
    textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 10 * 1000);
    textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 9);
    producer.send(textMessage);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    // 以下每小时投递一次
    textMessage = session
        .createTextMessage(
            "hello, test-topic-delay every hour " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    textMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");
    producer.send(textMessage);
    System.out.println("Send maybe OK. msg=" + textMessage.getText());

    // 9、关闭资源
    producer.close();
    session.close();
    connection.close();
  }

}
