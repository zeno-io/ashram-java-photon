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

package xyz.flysium.photon;

import com.google.common.util.concurrent.RateLimiter;

/**
 * <code>RateLimiter</code> in Guava (https://github.com/google/guava)
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class RateLimiterTest {

  public static void main(String[] args) {
    // RateLimiter.create(2)每秒产生的令牌数
    RateLimiter limiter = RateLimiter.create(2);

    // 平滑限流，从冷启动速率（满的）到平均消费速率的时间间隔
//    RateLimiter limiter = RateLimiter.create(2, 1000L, TimeUnit.MILLISECONDS);

    // limiter.acquire() 阻塞的方式获取令牌
    System.out.println(limiter.acquire()); // 返回表示获得令牌消耗的时间
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(limiter.acquire(5)); // 可以通过取若干个令牌，动态调整 QPS
    System.out.println(limiter.acquire());
    System.out.println(limiter.acquire());
    System.out.println(limiter.acquire());
    System.out.println(limiter.acquire());
    System.out.println(limiter.acquire());
  }

}

