package xyz.flysium.photon.jianzhioffer.sort.hard;

import java.util.PriorityQueue;

/**
 * 剑指 Offer 41. 数据流中的中位数
 * <p>
 * https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/
 *
 * @author zeno
 */
public class J0041_MedianFinder {

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
    MedianFinder solution = new J0041_MedianFinder().new MedianFinder();
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

  // 执行用时：81 ms, 在所有 Java 提交中击败了56.82% 的用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class MedianFinder {

    private PriorityQueue<Integer> greater;
    private PriorityQueue<Integer> less;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
      greater = new PriorityQueue<>();// 小顶堆，保存较大的一半
      less = new PriorityQueue<>((x, y) -> (y - x)); // 大顶堆，保存较小的一半
    }

    public void addNum(int num) {
      if (greater.size() != less.size()) {
        greater.offer(num);
        less.offer(greater.poll());
      } else {
        less.offer(num);
        greater.offer(less.poll());
      }
    }

    public double findMedian() {
      if (greater.size() != less.size()) {
        return greater.peek();
      }
      return (greater.peek() + less.peek()) / 2.0;
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
