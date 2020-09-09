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
import java.util.concurrent.Future;
import kafka.classic.samples.KafkaTest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class Producer extends KafkaTest {

  public static final String TOPIC = "DemoTopic1";

  public static void main(String[] args) {
    setKafkaLogger(Level.INFO);

    Properties props = new Properties();
    // Kafka 服务器地址
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducerClient1");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        org.apache.kafka.common.serialization.StringSerializer.class.getName());
    // KafkaProducer 是线程安全的，可以在多个线程中共享单个 KafkaProducer 实例，也可以将 KafkaProducer 实例进行池化来供其他线程调用。
    KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);

    int messageNo = 1;
    String messageStr = "Message_" + messageNo;
    long startTime = System.currentTimeMillis();
    // TODO 同步发送
    try {
      Future<RecordMetadata> future = producer.send(new ProducerRecord<>(TOPIC, messageNo,
          messageStr));
      // 调用 Future 对象的 get() 方法等待 Kafka 响应。
      RecordMetadata metadata = future.get();
      long elapsedTime = System.currentTimeMillis() - startTime;
//      System.out.println("==== Sent message: (" + messageNo + ", " + messageStr + ")");
      System.out.println(
          "==== Sent message: (" + messageStr
              + ") sent to partition(" + metadata.partition()
              + "), " + "offset(" + metadata.offset()
              + ") in " + elapsedTime + " ms");
    } catch (Exception e) {
      //  KafkaProducer 一般会发生两种类型的异常 可重试的异常和不可重试的异常 。
      //  常见的可重试异常有 NetworkException LeaderNotAvailableException UnknownTopicOrPartitionException NotEnoughReplicasException NotCoordinatorException 等。
      //  比如 NetworkException 表示网络异常，这个有可能是由于网络瞬时故障而导致的异常，可以通过重试解决；
      //  又比如 LeaderNotAvailableException 表示分区的 leader 副本不可用，这个异常通常发生在 leader 副本下线而新的 leader 副本选举完成之前，重试之后可以重新恢复。
      //  不可重试的异常，比如 RecordTooLargeException 异常，暗示了所发送的消息太大， KafkaProducer 对此不会进行任何重试 直接抛出异常
      e.printStackTrace();
    }

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // close() 方法会阻塞等待之 所有 发送请求完成后再关 KafkaProducer 。
        producer.close();
      }
    });
  }

}
