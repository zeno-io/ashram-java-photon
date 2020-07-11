package xyz.flysium.photon.limiter;

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

