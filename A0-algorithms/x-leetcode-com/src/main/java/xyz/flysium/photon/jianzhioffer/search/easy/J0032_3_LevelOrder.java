package xyz.flysium.photon.jianzhioffer.search.easy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 剑指 Offer 32 - III. 从上到下打印二叉树 III
 * <p>
 * https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-iii-lcof/
 *
 * @author zeno
 */
public class J0032_3_LevelOrder {

//请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
//
//
//
// 例如:
//给定二叉树: [3,9,20,null,null,15,7],
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
//
// 返回其层次遍历结果：
//
// [
//  [3],
//  [20,9],
//  [15,7]
//]
//
//
//
//
// 提示：
//
//
// 节点总数 <= 1000
//
// Related Topics 树 广度优先搜索
// 👍 50 👎 0


  public static void main(String[] args) {
    Solution solution = new J0032_3_LevelOrder().new Solution();

  }

// 	执行耗时:1 ms,击败了99.84% 的Java用户
//leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<List<Integer>> l = new LinkedList<>();
      Queue<TreeNode> queue = new LinkedList<>();
      int level = 1;

      queue.offer(root);
      while (!queue.isEmpty()) {
        LinkedList<Integer> cl = new LinkedList<>();
        l.add(cl);
        int size = queue.size();
        boolean odd = (level & (~level + 1)) == 1;
        for (int i = 0; i < size; i++) {
          TreeNode node = queue.poll();
          if (odd) {
            cl.addLast(node.val);
          } else {
            cl.addFirst(node.val);
          }
          if (node.left != null) {
            queue.offer(node.left);
          }
          if (node.right != null) {
            queue.offer(node.right);
          }
        }
        level++;
      }
      return l;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)


}
