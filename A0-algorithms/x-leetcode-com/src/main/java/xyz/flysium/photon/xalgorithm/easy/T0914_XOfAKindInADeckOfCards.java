package xyz.flysium.photon.xalgorithm.easy;

/**
 * 914. 卡牌分组
 * <p>
 * https://leetcode-cn.com/problems/x-of-a-kind-in-a-deck-of-cards/
 *
 * @author zeno
 */
public class T0914_XOfAKindInADeckOfCards {

//给定一副牌，每张牌上都写着一个整数。
//
// 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
//
//
// 每组都有 X 张牌。
// 组内所有的牌上都写着相同的整数。
//
//
// 仅当你可选的 X >= 2 时返回 true。
//
//
//
// 示例 1：
//
// 输入：[1,2,3,4,4,3,2,1]
//输出：true
//解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
//
//
// 示例 2：
//
// 输入：[1,1,1,2,2,2,3,3]
//输出：false
//解释：没有满足要求的分组。
//
//
// 示例 3：
//
// 输入：[1]
//输出：false
//解释：没有满足要求的分组。
//
//
// 示例 4：
//
// 输入：[1,1]
//输出：true
//解释：可行的分组是 [1,1]
//
//
// 示例 5：
//
// 输入：[1,1,2,2,2,2]
//输出：true
//解释：可行的分组是 [1,1]，[2,2]，[2,2]
//
//
//
//提示：
//
//
// 1 <= deck.length <= 10000
// 0 <= deck[i] < 10000
//
//
//
// Related Topics 数组 数学
// 👍 200 👎 0


  public static void main(String[] args) {
    Solution solution = new T0914_XOfAKindInADeckOfCards().new Solution();
    solution.hasGroupsSizeX(new int[]{1, 1, 1, 1, 2, 2, 2, 2, 2, 2});
  }

  // 执行耗时:2 ms,击败了97.77% 的Java用户

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

//    public boolean hasGroupsSizeX(int[] deck) {
//      Map<Integer, Integer> m = new HashMap<>();
//      for (int d : deck) {
//        m.put(d, m.getOrDefault(d, 0) + 1);
//      }
//      if (m.size() < 2) {
//        return m.values().iterator().next() >= 2;
//      }
//      Iterator<Integer> it = m.values().iterator();
//      int e = getMaxDivisor(it.next(), it.next());
//      while (it.hasNext()) {
//        Integer cnt = it.next();
//        e = getMaxDivisor(e, cnt);
//      }
//      return e >= 2;
//    }

    public boolean hasGroupsSizeX(int[] deck) {
      int max = 0;
      for (int d : deck) {
        max = Math.max(max, d);
      }
      int[] count = new int[max + 1];
      for (int d : deck) {
        count[d]++;
      }
      int x = -1;
      for (int cnt : count) {
        if (cnt > 0) {
          if (x == -1) {
            x = cnt;
          } else {
            x = getMaxDivisor(x, cnt);
          }
        }
      }
      return x >= 2;
    }

    // 最大公约数
    private int getMaxDivisor(int m, int n) {
      if (m < n) {
        // swap
        m = m ^ n;
        n = m ^ n;
        m = m ^ n;
      }
      while (m % n != 0) {
        int v = m % n;
        m = n;
        n = v;
      }
      return n;
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
