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

package rocketmq.classic.samples.tracemessage;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class TraceProducer {

  public static void main(String[] args) throws MQClientException, InterruptedException {

    DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName", true);
    producer.setNamesrvAddr("127.0.0.1:9876");
    producer.start();

      for (int i = 0; i < 128; i++) {
          try {
              {
                  Message msg = new Message("TopicTest",
                      "TagA",
                      "OrderID188",
                      "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                  SendResult sendResult = producer.send(msg);
                  System.out.printf("%s%n", sendResult);
              }

          } catch (Exception e) {
              e.printStackTrace();
          }
      }

    producer.shutdown();
  }
}
