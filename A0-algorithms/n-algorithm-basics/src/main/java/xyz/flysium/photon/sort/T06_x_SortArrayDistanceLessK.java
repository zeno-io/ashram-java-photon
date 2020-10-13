package xyz.flysium.photon.sort;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import xyz.flysium.photon.ArraySupport;

/**
 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
 * <p>
 * 请选择一个合适的排序策略，对这个数组进行排序。
 *
 * @author zeno
 */
public class T06_x_SortArrayDistanceLessK {

  public static void main(String[] args) {
    T06_x_SortArrayDistanceLessK distanceLessK = new T06_x_SortArrayDistanceLessK();
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int testTime = 500000;
    int maxSize = 100;
    int maxValue = 100;
    for (int i = 0; i < testTime; i++) {
      int k = random.nextInt(maxSize);
      int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
      int[] arr1 = new int[arr.length];
      int[] arr2 = new int[arr.length];
      System.arraycopy(arr, 0, arr1, 0, arr.length);
      System.arraycopy(arr, 0, arr2, 0, arr.length);
      Arrays.sort(arr1);
      distanceLessK.sort(arr2, k);
      if (!ArraySupport.equals(arr1, arr2)) {
        System.out.println("-> Wrong algorithm !!!");
        System.out.println("K : " + k);
        System.out.println(ArraySupport.toString(arr));
        System.out.println(ArraySupport.toString(arr1));
        System.out.println(ArraySupport.toString(arr2));
        break;
      }
    }
    System.out.println("Finish !");
  }

  public void sort(int[] arr, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();

    int k0 = Math.min(k + 1, arr.length);
    int index = 0;
    for (int i = 0; i < k0; i++) {
      heap.add(arr[i]);
    }
    arr[index++] = heap.poll();

    for (int i = k0; i < arr.length; i++) {
      heap.add(arr[i]);
      arr[index++] = heap.poll();
    }

    while (!heap.isEmpty()) {
      arr[index++] = heap.poll();
    }
  }

  private static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int k) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int size = random.nextInt(maxSize) + 1;
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) {
      arr[i] = random.nextInt(maxValue);
    }

    Arrays.sort(arr);

    boolean[] isSwap = new boolean[arr.length];
    for (int i = 0; i < size; i++) {
      int j = Math.min(i + random.nextInt(k + 1), arr.length - 1);
      if (!isSwap[i] && !isSwap[j]) {
        isSwap[i] = true;
        isSwap[j] = true;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
      }
    }
    return arr;
  }

}
