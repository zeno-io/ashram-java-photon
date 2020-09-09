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

package kafka.classic.samples.simple;

import ch.qos.logback.classic.Level;
import java.util.Properties;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 生产者：异步发送
 *
 * @author Sven Augustus
 */
public class AsyncProducer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducerClient2");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringSerializer.class.getName());
    KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

    int messageNo = 1;
    String messageStr = "Message_" + messageNo;

    long startTime = System.currentTimeMillis();
    // TODO 异步发送
    producer.send(new ProducerRecord<>(TOPIC, messageNo, messageStr), new Callback() {

      @Override
      public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
          System.out.println(
              "==== message(" + messageStr
                  + ") sent to partition(" + metadata.partition()
                  + "), " + "offset(" + metadata.offset()
                  + ") in " + elapsedTime + " ms");
        } else {
          // TODO 在实际应用中应该使用更加稳妥的方式来处理，比如可以将异常记录以便日后分析，也可以做一定的处理来进行消息重发。
          exception.printStackTrace();
        }
      }
    });

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        producer.close();
      }
    });
  }

}
