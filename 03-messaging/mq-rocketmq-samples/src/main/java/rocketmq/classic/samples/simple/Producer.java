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
package rocketmq.classic.samples.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class Producer {

  public static void main(String[] args) throws MQClientException, InterruptedException {
    DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
    producer.setNamesrvAddr("127.0.0.1:9876");
    // FIXME 发送端的 send 方法本身支持内部重试，重试逻辑如下：
    //  a)至多重试2次；
    //  b)如果发送失败，则轮转到下一个broker；
    //  c)这个方法的总耗时不超过sendMsgTimeout 设置的值，默认 3s，超过时间不在重试。
    producer.setRetryTimesWhenSendFailed(2);
//    producer.setRetryTimesWhenSendAsyncFailed(2);
    producer.setSendMsgTimeout(3000);
    // TODO VipChannel 默认为true，占用10909端口，此时需要开放10909端口，否则会报 ：connect to <：10909> failed异常，可以直接设置为false
    producer.setVipChannelEnabled(true);
    // TODO 启动，如果失败，建议增加日志打印
    producer.start();

    for (int i = 0; i < 128; i++) {
      try {
        {
          Message msg = new Message("TopicTest",
              /* TODO 消息Tag, 可以用于消费过滤 */
              "TagA",
              /* TODO 消息key, 可以用于消息查询 */
              "OrderID188",
              /* TODO 消息体内容, 要求生产者与消费者序列化方案一致，这样数据才能正常反解析 */
              "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
          // FIXME 这里注意发送如果失败，会自动重试，重试次数：retryTimesWhenSendFailed
          SendResult sendResult = producer.send(msg);
          System.out.printf("%s%n", sendResult);
          if (SendStatus.FLUSH_DISK_TIMEOUT.equals(sendResult.getSendStatus())
              || SendStatus.FLUSH_SLAVE_TIMEOUT.equals(sendResult.getSendStatus())) {
            //  FIXME 重复或丢失
            //    如果您得到FLUSH_DISK_TIMEOUT、FLUSH_SLAVE_TIMEOUT并且 Broker 恰好在此时意外宕机，您会发现你的消息丢失。
            //    此时，您有两个选择，一个是不管它，这可能导致这个消息丢失；另一个是重新发送消息，这可能会导致消息重复。
            //    我们经常建议重新发送，然后再消费时使用某个方法移除重复的消息。除非你觉得一些信息丢失并不重要。
            //    但是请记住，当您得到 SLAVE_NOT_AVAILABLE 状态时，重新发送是没有用的。
            //    如果出现这种情况，您应该保存场景并通知集群管理员 （Cluster Manager）。
            System.err.printf("Send Failed, result = %s%n", sendResult);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    producer.shutdown();
    // FIXME 如果是Servlet 容器，建议使用 shutdownHook 钩子
    //    Runtime.getRuntime().addShutdownHook(new Thread(producer::shutdown));
  }

}

