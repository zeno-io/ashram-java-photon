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

package kafka.classic.samples;

import java.beans.IntrospectionException;
import org.apache.kafka.common.config.ConfigDef;

/**
 * 生产者、消费者配置参数打印
 *
 * @author Sven Augustus
 */
public class ProducerAndConsumerConfigTest {

  public static void main(String[] args)
      throws IntrospectionException, NoSuchFieldException, IllegalAccessException {
    System.out .println(new ConfigDef().withClientSslSupport().toHtmlTable());
    System.out.println("---------------------------------------------------------------");
    System.out .println(new ConfigDef().withClientSaslSupport().toHtmlTable());
    System.out.println("---------------------------------------------------------------");
    // ProducerConfig.main(args);
    System.out.println("---------------------------------------------------------------");
    // ConsumerConfig.main(args);
  }

}
