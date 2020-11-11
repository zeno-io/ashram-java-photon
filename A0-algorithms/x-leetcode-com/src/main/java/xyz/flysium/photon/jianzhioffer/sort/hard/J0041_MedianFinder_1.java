package xyz.flysium.photon.jianzhioffer.sort.hard;

/**
 * 剑指 Offer 41. 数据流中的中位数
 * <p>
 * https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/
 *
 * @author zeno
 */
public class J0041_MedianFinder_1 {

//如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数
//值排序之后中间两个数的平均值。
//
// 例如，
//
// [2,3,4] 的中位数是 3
//
// [2,3] 的中位数是 (2 + 3) / 2 = 2.5
//
// 设计一个支持以下两种操作的数据结构：
//
//
// void addNum(int num) - 从数据流中添加一个整数到数据结构中。
// double findMedian() - 返回目前所有元素的中位数。
//
//
// 示例 1：
//
// 输入：
//["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
//[[],[1],[2],[],[3],[]]
//输出：[null,null,null,1.50000,null,2.00000]
//
//
// 示例 2：
//
// 输入：
//["MedianFinder","addNum","findMedian","addNum","findMedian"]
//[[],[2],[],[3],[]]
//输出：[null,null,2.00000,null,2.50000]
//
//
//
// 限制：
//
//
// 最多会对 addNum、findMedian 进行 50000 次调用。
//
//
// 注意：本题与主站 295 题相同：https://leetcode-cn.com/problems/find-median-from-data-strea
//m/
// Related Topics 堆 设计
// 👍 76 👎 0


  public static void main(String[] args) {
    MedianFinder solution = new J0041_MedianFinder_1().new MedianFinder();
    solution.addNum(-1);
    System.out.println(solution.findMedian());
    solution.addNum(-2);
    System.out.println(solution.findMedian());
    solution.addNum(-3);
    System.out.println(solution.findMedian());
    solution.addNum(-4);
    System.out.println(solution.findMedian());
    solution.addNum(-5);
    System.out.println(solution.findMedian());
  }

  // 执行耗时:777 ms,击败了7.78% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class MedianFinder {

    private int[] arr;
    private int capacity;
    private int size = 0;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
      capacity = 16;
      arr = new int[capacity];
    }

    public void addNum(int num) {
      ensureCapacity();
      arr[size] = num;
      size++;
      if (size == 1) {
        return;
      }
      // Arrays.sort(arr, 0, size);
      // insertion sort
      int curr = size - 1;
      int prev = curr - 1;
      while (curr >= 1) {
        if (arr[curr] >= arr[prev]) {
          break;
        }
        swap(arr, curr, prev);
        curr = prev;
        prev = curr - 1;
      }
    }

    public double findMedian() {
      if (size == 0) {
        return 0;
      } else if (size == 1) {
        return arr[0];
      } else if (size == 2) {
        return (arr[0] + arr[1]) / 2.0;
      }
      int mid = (size - 1) >> 1;
      if ((size & (~size + 1)) == 1) {
        return arr[mid];
      }
      int a = arr[mid];
      int b = arr[mid + 1];
      return (a + b) / 2.0;
    }

    private void ensureCapacity() {
      if (size < capacity) {
        return;
      }
      int newCapacity = capacity + (capacity >> 1);
      int[] newArr = new int[newCapacity];
      System.arraycopy(arr, 0, newArr, 0, capacity);
      arr = newArr;
      capacity = newCapacity;
    }

    private void swap(int[] array, int i, int j) {
      if (i == j) {
        return;
      }
      array[i] = array[i] ^ array[j];
      array[j] = array[i] ^ array[j];
      array[i] = array[i] ^ array[j];
    }

  }

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
//leetcode submit region end(Prohibit modification and deletion)


}
