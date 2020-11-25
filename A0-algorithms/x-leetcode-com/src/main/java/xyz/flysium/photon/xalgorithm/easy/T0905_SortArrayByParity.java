package xyz.flysium.photon.xalgorithm.easy;

/**
 * 905. 按奇偶排序数组
 * <p>
 * https://leetcode-cn.com/problems/sort-array-by-parity/
 *
 * @author zeno
 */
public class T0905_SortArrayByParity {

//给定一个非负整数数组 A，返回一个数组，在该数组中， A 的所有偶数元素之后跟着所有奇数元素。
//
// 你可以返回满足此条件的任何数组作为答案。
//
//
//
// 示例：
//
// 输入：[3,1,2,4]
//输出：[2,4,3,1]
//输出 [4,2,3,1]，[2,4,1,3] 和 [4,2,1,3] 也会被接受。
//
//
//
//
// 提示：
//
//
// 1 <= A.length <= 5000
// 0 <= A[i] <= 5000
//
// Related Topics 数组
// 👍 178 👎 0


  public static void main(String[] args) {
    Solution solution = new T0905_SortArrayByParity().new Solution();

  }

  // 执行耗时:1 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] sortArrayByParity(int[] A) {
      int l = 0;
      int r = A.length - 1;
      while (l < r) {
        while (l < r && (A[l] & 1) != 1) {
          l++;
        }
        while (r > l && (A[r] & 1) == 1) {
          r--;
        }
        if (l >= r) {
          break;
        }
        swap(A, l, r);
      }
      return A;
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
//leetcode submit region end(Prohibit modification and deletion)


}
