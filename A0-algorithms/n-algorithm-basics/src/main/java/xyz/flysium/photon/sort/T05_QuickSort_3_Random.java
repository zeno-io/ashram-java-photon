package xyz.flysium.photon.sort;

import java.util.concurrent.ThreadLocalRandom;
import xyz.flysium.photon.CommonSort;
import xyz.flysium.photon.SortSupport;

/**
 * 随机快排: 在 快速排序算法2 基础上，每次选取的基准不再是固定最右，而是随机一个位置的
 *
 * @author zeno
 */
public class T05_QuickSort_3_Random extends T05_QuickSort_2 {

  public static void main(String[] args) {
    SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T05_QuickSort_3_Random()::sort);
    System.out.println("Finish !");
  }

  @Override
  public void sort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }
    process(arr, 0, arr.length - 1);
  }

  private final ThreadLocalRandom random = ThreadLocalRandom.current();

  private void process(int[] arr, int l, int r) {
    if (l >= r) {
      return;
    }
    // 随机一个位置
    int numIndex = random.nextInt(l, r + 1);
    CommonSort.swap(arr, numIndex, r);
    PositionPair p = netherLandsFlag(arr, l, r);
    process(arr, l, p.eqStart - 1);
    process(arr, p.eqEnd + 1, r);
  }


}
