package xyz.flysium.photon.jianzhioffer.search.medium;

import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 26. 树的子结构
 * <p>
 * https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/
 *
 * @author zeno
 */
public class J0026_IsSubStructure {

//输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
//
// B是A的子结构， 即 A中有出现和B相同的结构和节点值。
//
// 例如:
//给定的树 A:
//
// 3
// / \
// 4 5
// / \
// 1 2
//给定的树 B：
//
// 4
// /
// 1
//返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
//
// 示例 1：
//
// 输入：A = [1,2,3], B = [3,1]
//输出：false
//
//
// 示例 2：
//
// 输入：A = [3,4,5,1,2], B = [4,1]
//输出：true
//
// 限制：
//
// 0 <= 节点个数 <= 10000
// Related Topics 树
// 👍 130 👎 0


  public static void main(String[] args) {
    Solution solution = new J0026_IsSubStructure().new Solution();

  }

  // 执行耗时:0 ms,击败了100.00% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  class Solution {

    public boolean isSubStructure(TreeNode A, TreeNode B) {
      if (B == null) {
        return false;
      }
      return dfs(A, B);
    }

    private boolean dfs(TreeNode A, TreeNode B) {
      if (A == null || B == null) {
        return false;
      }
      // 以A为头的树包含B的结构
      // 或 A的左子树能匹配
      // 或 A的右子树能匹配
      return recur(A, B) || dfs(A.left, B) || dfs(A.right, B);
    }

    // 以A为头的树包含B的结构
    private boolean recur(TreeNode A, TreeNode B) {
      // B 为空，说明同步走先序，B已经完成，B是A的子结构，返回 true
      if (B == null) {
        return true;
      }
      // B 不为空，A 为空，不符合
      if (A == null) {
        return false;
      }
      // 如果值不同，不符合
      if (A.val != B.val) {
        return false;
      }
      // 值相同，继续判断 A的左子节点和 B的右子节点，且 A的左子节点和 B的右子节点
      return recur(A.left, B.left) && recur(A.right, B.right);
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
