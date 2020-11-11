package xyz.flysium.photon.jianzhioffer.search.medium;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.StringSupport;
import xyz.flysium.photon.TreeSupport;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 34. 二叉树中和为某一值的路径
 * <p>
 * https://leetcode-cn.com/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof/
 *
 * @author zeno
 */
public class J0034_PathSum {

//输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
//
//
//
// 示例:
//给定如下二叉树，以及目标和 sum = 22，
//
//               5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \    / \
//        7    2  5   1
//
//
// 返回:
//
// [
//   [5,4,11,2],
//   [5,8,4,5]
//]
//
//
//
//
// 提示：
//
//
// 节点总数 <= 10000
//
//
// 注意：本题与主站 113 题相同：https://leetcode-cn.com/problems/path-sum-ii/
// Related Topics 树 深度优先搜索
// 👍 101 👎 0


  public static void main(String[] args) {
    Solution solution = new J0034_PathSum().new Solution();
    System.out.println(
      solution.pathSum(TreeSupport.newBinaryTree(ArraySupport.newIntegerArray("[1,2]")), 1));
    System.out.println(solution
      .pathSum(TreeSupport.newBinaryTree(ArraySupport.newIntegerArray(
        StringSupport.appendStringForJSON("[1,-2,-3,1,3,-2,null,-1]"))),
        -1));
  }

  // 执行用时：1 ms, 在所有 Java 提交中击败了100.00% 的用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    List<List<Integer>> ans = new LinkedList<>();
    LinkedList<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
      if (root == null) {
        return Collections.emptyList();
      }
      dfs(root, sum);
      return ans;
    }

    private void dfs(TreeNode root, int sum) {
      if (root == null) {
        return;
      }
      path.add(root.val);
      if (root.val == sum && root.left == null && root.right == null) {
        ans.add(new LinkedList<>(path));
      }
      dfs(root.left, sum - root.val);
      dfs(root.right, sum - root.val);
      // 路径恢复： 向上回溯前，需要将当前节点从路径 path 中删除
      path.removeLast();
    }

  }
//leetcode submit region end(Prohibit modification and deletion)


}
