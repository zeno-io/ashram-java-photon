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
package rocketmq.classic.samples.ordermessage;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {

  public static void main(String[] args) throws UnsupportedEncodingException {
    try {
      DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
      producer.setNamesrvAddr("127.0.0.1:9876");
      producer.start();

      String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
      for (int i = 0; i < 100; i++) {
        int orderId = i % 10;
        Message msg =
            new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i,
                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
        // FIXME 生产消息分布到不同的 MessageQueue，用于检验 顺序消费 是否能正常
        SendResult sendResult = producer.send(msg, new MessageQueueSelector() {

          @Override
          public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
            Integer id = (Integer) arg;
            int index = id % mqs.size();
            return mqs.get(index);
          }
        }, orderId);

        System.out.printf("%s%n", sendResult);
      }

      producer.shutdown();
    } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
