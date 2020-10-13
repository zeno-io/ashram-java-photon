package xyz.flysium.photon.sort;

import xyz.flysium.photon.CommonSort;
import xyz.flysium.photon.SortSupport;

/**
 * 快速排序算法2: (时间复杂度 N^2)
 * <p>
 * 利用 荷兰国旗问题，每次依据段内最右一个数进行划分 （<=区，=区, >区）那么得到=区范围，可以确定一批数的位置
 * <p>
 * 然后继续操作  <=区
 * <p>
 * 然后继续操作  >区
 *
 * @author zeno
 */
public class T05_QuickSort_2 {

  public static void main(String[] args) {
    SortSupport.testToEnd(100000, 10, 100, 0, 1000, new T05_QuickSort_2()::sort);
    System.out.println("Finish !");
  }

  // 在arr[L..R]范围上，进行快速排序的过程：
  // 1）用arr[R]对该范围做partition，< arr[R]的数在左部分，== arr[R]的数中间，>arr[R]的数在右部分。假设== arr[R]的数所在范围是[a,b]
  // 2）对arr[L..a-1]进行快速排序(递归)
  // 3）对arr[b+1..R]进行快速排序(递归)
  // 因为每一次partition都会搞定一批数的位置且不会再变动，所以排序能完成
  public void sort(int[] arr) {
    if (arr == null || arr.length < 2) {
      return;
    }
    process(arr, 0, arr.length - 1);
  }

  private void process(int[] arr, int l, int r) {
    if (l >= r) {
      return;
    }
    PositionPair p = netherLandsFlag(arr, l, r);
    process(arr, l, p.eqStart - 1);
    process(arr, p.eqEnd + 1, r);
  }

  protected PositionPair netherLandsFlag(int[] arr, int l, int r) {
    PositionPair pair = new PositionPair();
    if (l > r) {
      pair.eqStart = -1;
      pair.eqEnd = -1;
      return pair;
    }
    if (l == r) {
      pair.eqStart = l;
      pair.eqEnd = r;
      return pair;
    }
    final int num = arr[r];
    int ltq = l - 1;
    int gt = r + 1;
    int index = l;

    while (index < gt) {
      if (arr[index] == num) {
        index++;
      } else if (arr[index] < num) {
        CommonSort.swap(arr, index++, ++ltq);
      } else {
        CommonSort.swap(arr, index, --gt);
      }
    }
    pair.eqStart = ltq + 1;
    pair.eqEnd = gt - 1;

    return pair;
  }

}
