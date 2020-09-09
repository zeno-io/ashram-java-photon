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
package rocketmq.classic.samples.scheduledmessage;

import java.io.UnsupportedEncodingException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 生产者：延迟消息
 */
public class ProduceDelayMessage {

   // TODO Broker端 可以设置 messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
  public static final int DELAY_LEVEL = 3;

  public static void main(String[] args) throws UnsupportedEncodingException {
    try {
      DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
      producer.setNamesrvAddr("127.0.0.1:9876");
      producer.start();

      for (int i = 0; i < 100; i++) {
        Message msg = new Message("TopicTestDDD",
            "TagA",
            "OrderID188",
            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // TODO 这个消息将在 10s 后，消费者才会获取到
        msg.setDelayTimeLevel(DELAY_LEVEL);

        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
      }

      producer.shutdown();
    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
