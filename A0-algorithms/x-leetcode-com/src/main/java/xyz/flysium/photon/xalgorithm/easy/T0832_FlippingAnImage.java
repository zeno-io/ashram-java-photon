package xyz.flysium.photon.xalgorithm.easy;

/**
 * 832. 翻转图像
 * <p>
 * https://leetcode-cn.com/problems/flipping-an-image/
 *
 * @author zeno
 */
public class T0832_FlippingAnImage {

//给定一个二进制矩阵 A，我们想先水平翻转图像，然后反转图像并返回结果。
//
// 水平翻转图片就是将图片的每一行都进行翻转，即逆序。例如，水平翻转 [1, 1, 0] 的结果是 [0, 1, 1]。
//
// 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。例如，反转 [0, 1, 1] 的结果是 [1, 0, 0]。
//
// 示例 1:
//
//
//输入: [[1,1,0],[1,0,1],[0,0,0]]
//输出: [[1,0,0],[0,1,0],[1,1,1]]
//解释: 首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
//     然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
//
//
// 示例 2:
//
//
//输入: [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
//输出: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
//解释: 首先翻转每一行: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]]；
//     然后反转图片: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
//
//
// 说明:
//
//
// 1 <= A.length = A[0].length <= 20
// 0 <= A[i][j] <= 1
//
// Related Topics 数组
// 👍 192 👎 0


  public static void main(String[] args) {
    Solution solution = new T0832_FlippingAnImage().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[][] flipAndInvertImage(int[][] A) {
      if (A == null) {
        throw new IllegalArgumentException();
      }
      final int rows = A.length;
      final int cols = A[0].length;
      final int halfCol = (cols + 1) >> 1; // if cols is odd, contains the middle one

      for (int[] ints : A) {
        for (int j = 0; j < halfCol; j++) {
          int tmp = ints[j] ^ 1;
          ints[j] = ints[cols - 1 - j] ^ 1;
          ints[cols - 1 - j] = tmp;
        }
      }
      return A;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
