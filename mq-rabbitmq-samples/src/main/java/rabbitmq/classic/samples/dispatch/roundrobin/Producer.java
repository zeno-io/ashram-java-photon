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

package rabbitmq.classic.samples.dispatch.roundrobin;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @author Sven Augustus
 */
public class Producer extends Declare {

  public static void main(String[] args)
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, InterruptedException {
    // 声明队列
    Channel channel = prepare();

    byte[] messageBodyBytes = ("Hello World! " + LocalDateTime.now()).getBytes();
    // 发送消息，注意如果发送失败，不会自动重发
    for (int i = 0; i < 50; i++) {
      String msg = String.valueOf((i + 1));
      // 这里仅做测试，不做持久化
      channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
      System.out.println("Send : " + msg);
      TimeUnit.MILLISECONDS.sleep(50);
    }

    channel.close();
    channel.getConnection().close();
  }

}
