package xyz.flysium.photon.algorithm.array.basic;

/**
 * 11. 盛最多水的容器
 * <p>
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * @author zeno
 */
public class T0011_ContainerWithMostWater {

//给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i,
//ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
// 说明：你不能倾斜容器，且 n 的值至少为 2。
//
//
//
//
//
// 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
//
//
//
// 示例：
//
// 输入：[1,8,6,2,5,4,8,3,7]
//输出：49
// Related Topics 数组 双指针
// 👍 1937 👎 0


  public static void main(String[] args) {
    Solution solution = new T0011_ContainerWithMostWater().new Solution();
    System.out.println(solution.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
  }

  // 执行耗时:3 ms,击败了92.73% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxArea(int[] height) {
      int l = 0;
      int r = height.length - 1;
      int max = 0;
      while (l < r) {
        int lh = height[l];
        int rh = height[r];
        int len = (r - l);
        if (lh > rh) {
          max = Math.max(max, len * rh);
          r--;
        } else {
          max = Math.max(max, len * lh);
          l++;
        }
      }
      return max;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
