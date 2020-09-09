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
package rocketmq.classic.samples.openmessaging;

import io.openmessaging.Message;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.OMSBuiltinKeys;
import io.openmessaging.consumer.PullConsumer;
import io.openmessaging.producer.Producer;
import io.openmessaging.producer.SendResult;

public class SimplePullConsumer {
    public static void main(String[] args) {
        final MessagingAccessPoint messagingAccessPoint =
            OMS.getMessagingAccessPoint("oms:rocketmq://localhost:9876/default:default");

        messagingAccessPoint.startup();

        final Producer producer = messagingAccessPoint.createProducer();

        final PullConsumer consumer = messagingAccessPoint.createPullConsumer(
            OMS.newKeyValue().put(OMSBuiltinKeys.CONSUMER_ID, "OMS_CONSUMER"));

        messagingAccessPoint.startup();
        System.out.printf("MessagingAccessPoint startup OK%n");

        final String queueName = "TopicTest";

        producer.startup();
        Message msg = producer.createBytesMessage(queueName, "Hello Open Messaging".getBytes());
        SendResult sendResult = producer.send(msg);
        System.out.printf("Send Message OK. MsgId: %s%n", sendResult.messageId());
        producer.shutdown();

        consumer.attachQueue(queueName);

        consumer.startup();
        System.out.printf("Consumer startup OK%n");

        // Keep running until we find the one that has just been sent
        boolean stop = false;
        while (!stop) {
            Message message = consumer.receive();
            if (message != null) {
                String msgId = message.sysHeaders().getString(Message.BuiltinKeys.MESSAGE_ID);
                System.out.printf("Received one message: %s%n", msgId);
                consumer.ack(msgId);

                if (!stop) {
                    stop = msgId.equalsIgnoreCase(sendResult.messageId());
                }

            } else {
                System.out.printf("Return without any message%n");
            }
        }

        consumer.shutdown();
        messagingAccessPoint.shutdown();
    }
}
