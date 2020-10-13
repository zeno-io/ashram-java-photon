package xyz.flysium.photon.sort;

import java.util.Comparator;
import java.util.PriorityQueue;
import xyz.flysium.photon.SortSupport;

/**
 * 堆排序
 *
 * @author zeno
 */
public class T06_HeapSort {

  public static void main(String[] args) {
    // 默认小根堆
    PriorityQueue<Integer> jdkMinHeap = new PriorityQueue<>();
    jdkMinHeap.add(6);
    jdkMinHeap.add(8);
    jdkMinHeap.add(0);
    jdkMinHeap.add(2);
    jdkMinHeap.add(9);
    jdkMinHeap.add(1);
    while (!jdkMinHeap.isEmpty()) {
      System.out.print(jdkMinHeap.poll() + " ");
    }
    System.out.println();

    SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T06_HeapSort()::sort);
    System.out.println("Finish !");
  }

  //  1，先让整个数组都变成大根堆结构，建立堆的过程:
//    1)从上到下的方法，时间复杂度为O(N*logN)
//    2)从下到上的方法，时间复杂度为O(N)
//  2，把堆的最大值和堆末尾的值交换，然后减少堆的大小之后，再去调整堆，一直周而复始，时间复杂度为O(N*logN)
//  3，堆的大小减小成0之后，排序完成
  public void sort(int[] arr) {
    Comparator<Integer> comparator = new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o1 > o2 ? -1 : (o1.intValue() == o2.intValue()) ? 0 : 1;
      }
    };
    // 针对给定一批数，而不是每次给一个数，可以使用 heapify 从堆底部以此，每个数从上到下堆 heapify, 时间复杂度为 O(N)
    // O(N*logN)
//    for (int i = 0; i < arr.length; i++) {
    // O(logN)
//      heapInsert(arr, i, comparator);
//    }
    int heapSize = arr.length;
    // O(N)
    for (int i = arr.length - 1; i >= 0; i--) {
      // T(N) = (N/2) * 1 + (N/4)*2 + (N/8)*3 + (N/16)*4 + ...
      // T(2N) = (N/2) * 2 + (N/2)*2 + (N/4)*3 + (N/8)*4 + ...
      // T(N) = N + (N/2)*1 + (N/4)*1 + (N/8)*1+ ... 约等于 2N
      heapify(arr, i, heapSize, comparator);
    }

    // O(N*logN)
    for (int i = arr.length - 1; i >= 0; i--) {
      // O(1)
      swap(arr, 0, --heapSize);
      // O(logN)
      heapify(arr, 0, heapSize, comparator);
    }
  }

  private void heapInsert(int[] arr, int index, Comparator<Integer> comparator) {
    int parent = (index - 1) >> 1;
    while (parent >= 0 && comparator.compare(arr[index], arr[parent]) > 0) {
      swap(arr, index, parent);
      index = parent;
      parent = (index - 1) >> 1;
    }
  }

  private void heapify(int[] arr, int index, int heapSize, Comparator<Integer> comparator) {
    int left = (index << 1) + 1;
    while (left < heapSize) {
      int right = left + 1;
      int largest =
        (right < heapSize && comparator.compare(arr[right], arr[left]) > 0) ? right : left;
      largest = comparator.compare(arr[largest], arr[index]) > 0 ? largest : index;
      if (largest == index) {
        break;
      }
      swap(arr, index, largest);
      index = largest;
      left = (index << 1) + 1;
    }
  }

  private void swap(int[] arr, int x, int y) {
    if (x == y) {
      return;
    }
    arr[x] = arr[x] ^ arr[y];
    arr[y] = arr[x] ^ arr[y];
    arr[x] = arr[x] ^ arr[y];
  }

}
