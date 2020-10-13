package xyz.flysium.photon.sort;

import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.CommonSort;

/**
 * 荷兰国旗问题
 * <p>
 * 给定一个数组arr，和一个整数num。请把小于num的数放在数组的左边，等于num的数放在中间，大于num的数放在数组的右边。
 * <p>
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 *
 * @author zeno
 */
public class T05_Partition2 {

  public static void main(String[] args) {
//    partition(new int[]{34, 93, 74, 84, 29, 15, 84, 95, 78, 24}, 24);
    int times = 100000;
    for (int i = 0; i < times; i++) {
      int[] origin = ArraySupport.generateRandomArray(10, 10, 0, 100);
      int[] other1 = new int[origin.length];
      int[] other2 = new int[origin.length];
      System.arraycopy(origin, 0, other1, 0, origin.length);
      System.arraycopy(origin, 0, other2, 0, origin.length);
      int num = origin[origin.length - 1];

      PositionPair expected = nativeButCorrectNetherLandsFlag(other1, num);
      PositionPair actual = netherLandsFlag(other2, num);

      if (expected.eqStart != actual.eqStart || expected.eqEnd != actual.eqEnd) {
        System.out.println("-> Wrong algorithm !!!");
      }
    }
    System.out.println("Finish !");
  }

  public static PositionPair netherLandsFlag(int[] arr, int num) {
    PositionPair pair = new PositionPair();
    final int length = arr.length;
    int lt = -1;
    int gt = arr.length;

    int index = 0;
    // 当 index 撞上大于区的左边界就可以停止
    while (index < gt) {
      if (arr[index] == num) {
        index++;
      } else if (arr[index] < num) {
        CommonSort.swap(arr, index++, ++lt);
      } else {
        // 注意，此时交换过来的是大于区左边的某个数，尚未观察，交换后，index 不能直接右移，交由下一回合继续观察
        CommonSort.swap(arr, index, --gt);
      }
    }

    pair.eqStart = lt + 1;
    pair.eqEnd = gt - 1;
    return pair;
  }

  public static PositionPair nativeButCorrectNetherLandsFlag(int[] arr, int num) {
    PositionPair pair = new PositionPair();
    int[] lt = new int[arr.length];
    int[] eq = new int[arr.length];
    int[] gt = new int[arr.length];
    int x = -1;
    int t = -1;
    int y = -1;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] < num) {
        lt[++x] = arr[i];
      } else if (arr[i] == num) {
        eq[++t] = arr[i];
      } else {
        gt[++y] = arr[i];
      }
    }
    if (x >= 0) {
      System.arraycopy(lt, 0, arr, 0, x + 1);
    }
    if (t >= 0) {
      System.arraycopy(eq, 0, arr, x + 1, t + 1);
    }
    if (y >= 0) {
      System.arraycopy(gt, 0, arr, x + t + 2, y + 1);
    }
    pair.eqStart = x + 1;
    pair.eqEnd = x + t + 1;
    return pair;
  }

}
