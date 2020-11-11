package xyz.flysium.photon.algorithm.array.basic;

/**
 * 215. 数组中的第K个最大元素
 * <p>
 * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 *
 * @author zeno
 */
public class T0215_KthLargestElementInAnArray {

//在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
//
// 示例 1:
//
// 输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
//
//
// 示例 2:
//
// 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4
//
// 说明:
//
// 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
// Related Topics 堆 分治算法
// 👍 753 👎 0


  public static void main(String[] args) {
    Solution solution = new T0215_KthLargestElementInAnArray().new Solution();
    //System.out.println(solution.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
    System.out.println(solution.findKthLargest(new int[]{7, 6, 5, 4, 3, 2, 1}, 5));
  }

  // 执行耗时:1 ms,击败了99.60% 的Java用户
  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int findKthLargest(int[] nums, int k) {
      int[] heap = new int[k + 2];
      int size = 1;
      for (int i = 0; i < k; i++) {
        heap[size++] = nums[i];
      }
      for (int i = size - 1; i >= 1; i--) {
        heapify(heap, size, i);
      }
      for (int i = k; i < nums.length; i++) {
        if (heap[1] < nums[i]) {
          heap[1] = nums[i];
          heapify(heap, size, 1);
        }
      }
      return heap[1];
    }

    private void heapify(int[] heap, int size, int i) {
      int l = i << 1;
      while (l < size) {
        int lessIdx = l + 1 < size && heap[l + 1] < heap[l] ? l + 1 : l;
        lessIdx = heap[lessIdx] < heap[i] ? lessIdx : i;
        if (lessIdx == i) {
          break;
        }
        swap(heap, i, lessIdx);
        i = lessIdx;
        l = i << 1;
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

//    public int findKthLargest(int[] nums, int k) {
//      PriorityQueue<Integer> minHeap = new PriorityQueue<>();
//      for (int num : nums) {
//        if (minHeap.size() < k) {
//          minHeap.offer(num);
//        } else if (minHeap.peek() < num) {
//          minHeap.poll();
//          minHeap.offer(num);
//        }
//      }
//      return minHeap.peek();
//    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
