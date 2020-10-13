package xyz.flysium.photon.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import xyz.flysium.photon.concurrent.TestSupport.MyBiConsumer;

/**
 * 1226. 哲学家进餐
 * <p>
 * 设计一个进餐规则（并行算法）使得每个哲学家都不会挨饿；也就是说，在没有人知道别人什么时候想吃东西或思考的情况下，每个哲学家都可以在吃饭和思考之间一直交替下去。
 * <p>
 * https://leetcode-cn.com/problems/the-dining-philosophers/
 *
 * @author zeno
 */
public class T1226_TheDiningPhilosophers {

  // 输入：每个哲学家需要进餐的次数
  private static final int TIMES = 50;
  private static final int PHILOSOPHER_SIZE = 5;

  public static void main(String[] args) throws InterruptedException {
    //输入：n = 1
    //输出：[[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],[0,2,1],[4,2,2],[3,2,1],[3,1,1],[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
    //解释:
    //n 表示每个哲学家需要进餐的次数。
    //输出数组描述了叉子的控制和进餐的调用，它的格式如下：
    //output[i] = [a, b, c] (3个整数)
    //- a 哲学家编号。
    //- b 指定叉子：{1 : 左边, 2 : 右边}.
    //- c 指定行为：{1 : 拿起, 2 : 放下, 3 : 吃面}。
    //如 [4,2,1] 表示 4 号哲学家拿起了右边的叉子。
    final StringBuffer buf = new StringBuffer("[");
    MyBiConsumer<DiningPhilosophers, StringBuffer>[] rs = new MyBiConsumer[PHILOSOPHER_SIZE];
    for (int k = 0; k < PHILOSOPHER_SIZE; k++) {
      // 哲学家的编号
      final int philosopher = k;
      rs[k] = (inst, buffer) -> {
        inst.wantsToEat(philosopher, () -> {
          String str = "[" + philosopher + ",1,1],";
          buffer.append(str);
        }, () -> {
          String str = "[" + philosopher + ",2,1],";
          buffer.append(str);
        }, () -> {
          String str = "[" + philosopher + ",0,3],";
          buffer.append(str);
        }, () -> {
          String str = "[" + philosopher + ",1,2],";
          buffer.append(str);
        }, () -> {
          String str = "[" + philosopher + ",2,2],";
          buffer.append(str);
        });
      };
    }
    long l = TestSupport.testWithMyBiConsumer(TIMES, false, DiningPhilosophers::new, buf, rs);

    if (buf.toString().endsWith(",")) {
      buf.deleteCharAt(buf.length() - 1);
    }
    buf.append("]");
    System.out.println(buf.toString());
    System.out.println(TestSupport.toMillisString(l));
  }

  private static class DiningPhilosophers {

    private final Lock[] forks = new ReentrantLock[5];
    // 限制 最多只有4个哲学家去持有叉子
    private final Semaphore eatLimit = new Semaphore(4);

    public DiningPhilosophers() {
      for (int i = 0; i < 5; i++) {
        forks[i] = new ReentrantLock(true);
      }
    }

    // philosopher 哲学家的编号。
    // pickLeftFork 和 pickRightFork 表示拿起左边或右边的叉子。
    // eat 表示吃面。
    // putLeftFork 和 putRightFork 表示放下左边或右边的叉子。
    // 由于哲学家不是在吃面就是在想着啥时候吃面，所以思考这个方法没有对应的回调。
    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
      Runnable pickLeftFork,
      Runnable pickRightFork,
      Runnable eat,
      Runnable putLeftFork,
      Runnable putRightFork) throws InterruptedException {
      final int leftFork = philosopher;
      final int rightFork = (philosopher + 1) % 5;
      eatLimit.acquire(1);
      forks[leftFork].lock();
      try {
        forks[rightFork].lock();
        try {
          pickLeftFork.run();
          pickRightFork.run();
          eat.run();
          putLeftFork.run();
          putRightFork.run();
        } finally {
          forks[rightFork].unlock();
        }
      } finally {
        forks[leftFork].unlock();
      }
      eatLimit.release(1);
    }
  }

}
