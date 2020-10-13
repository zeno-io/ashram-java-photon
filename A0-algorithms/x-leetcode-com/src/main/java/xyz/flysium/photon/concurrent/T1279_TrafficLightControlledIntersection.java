package xyz.flysium.photon.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 1279. 红绿灯路口
 *
 * @author zeno
 */
public class T1279_TrafficLightControlledIntersection {

  public static void main(String[] args) throws InterruptedException {
//    [1,3,5,2,4]
//    [2,1,2,4,3]
//    [10,20,30,40,50]
    test(new int[]{1, 3, 5, 2, 4}, new int[]{2, 1, 2, 4, 3}, new int[]{10, 20, 30, 40, 50});
//    [1,2,3,4,5]
//    [2,4,3,3,1]
//    [10,20,30,40,40]
    test(new int[]{1, 2, 3, 4, 5}, new int[]{2, 4, 3, 3, 1}, new int[]{10, 20, 30, 40, 40});
  }

  private static void test(int[] cars, int[] directions, int[] arrivalTimes)
    throws InterruptedException {
    // TODO 测试用例还待改进 ?
    TrafficLight trafficLight = new TrafficLight();
    AtomicBoolean aGreen = new AtomicBoolean(true);
    AtomicBoolean bGreen = new AtomicBoolean(false);
    Thread[] ts = new Thread[arrivalTimes.length];
    for (int i = 0; i < arrivalTimes.length; i++) {
      final int index = i;
      Runnable r = () -> {
        final int roadId = directions[index] <= 2 ? 1 : 2;
        trafficLight.carArrived(cars[index], roadId, directions[index], () -> {
          if (roadId == 1) {
            if (!aGreen.compareAndSet(false, true)) {
              System.err
                .println("Error~ Traffic Light On Road A Is Green");
              throw new IllegalStateException();
            }
            bGreen.compareAndSet(true, false);
          } else {
            if (!bGreen.compareAndSet(false, true)) {
              System.err
                .println("Error~ Traffic Light On Road B Is Green");
              throw new IllegalStateException();
            }
            aGreen.compareAndSet(true, false);
          }
          System.out.println("Traffic Light On Road " + (roadId == 1 ? "A" : "B") + " Is Green");
        }, () -> {
          System.out
            .println("Car " + cars[index] + " Has Passed Road " + (roadId == 1 ? "A" : "B")
              + " In Direction " + directions[index]);
        });
      };
      ts[i] = new Thread(r);
    }
    List<List<Integer>> ls = new LinkedList<>();
    ls.add(new LinkedList<>());
    int index = 0;
    for (int i = 0; i < arrivalTimes.length; i++) {
      if (i == 0 || (arrivalTimes[i] == arrivalTimes[i - 1])) {
        ls.get(index).add(i);
      } else {
        index++;
        if (ls.size() <= index) {
          ls.add(new LinkedList<>());
        }
        ls.get(index).add(i);
      }
    }
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (List<Integer> l : ls) {
      while (!l.isEmpty()) {
        Integer i = l.remove(random.nextInt(l.size()));
        ts[i].start();
      }
      TimeUnit.MILLISECONDS.sleep(5);
    }
    System.out.println();
  }

  private static class TrafficLight {

    // 1 (road A) or 2 (road B)
    private volatile int greenRoadId = 1;

    public TrafficLight() {

    }

    public void carArrived(
      int carId,           // ID of the car
      int roadId,          // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
      int direction,       // Direction of the car 1 <= directions[i] <= 4
      Runnable turnGreen,  // Use turnGreen.run() to turn light to green on current road
      Runnable crossCar    // Use crossCar.run() to make car cross the intersection
    ) {
      synchronized (this) {
        if (roadId != greenRoadId) {
          greenRoadId = roadId;
          turnGreen.run();
        }
        crossCar.run();
      }
    }

  }

}
