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

package kafka.classic.samples.simple;

import ch.qos.logback.classic.Level;
import java.util.Properties;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.producer.BufferExhaustedException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class OnewayProducer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    // Kafka 服务器地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducerClient3");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringSerializer.class.getName());
    KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

    int messageNo = 1;
    String messageStr = "Message_" + messageNo;
    // TODO 发送并忘记（fire-and-forget）
    //  大多数情况下，消息会正常到达，因为 Kafka 是高可用的，而且生产者会自动尝试重发。
    //  不过，使用这种方式有时候也会丢失一些消息。
    try {
      producer.send(new ProducerRecord<>(TOPIC, messageNo, messageStr));
      System.out.println("==== Sent message: (" + messageNo + ", " + messageStr + ")");
    } catch (SerializationException e) {
      // 序列化消息失败
      e.printStackTrace();
    } catch (BufferExhaustedException | TimeoutException e) {
      // 缓冲区已满
      e.printStackTrace();
    } catch (InterruptException e) {
      // 发送线程被中断
      e.printStackTrace();
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        producer.close();
      }
    });
  }

}
