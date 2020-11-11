package xyz.flysium.photon.algorithm.hash.basic.keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 652. 寻找重复的子树
 * <p>
 * https://leetcode-cn.com/problems/find-duplicate-subtrees/
 *
 * @author zeno
 */
public interface T0652_FindDuplicateSubtrees {

  // 给定一棵二叉树，返回所有重复的子树。对于同一类的重复子树，你只需要返回其中任意一棵的根结点即可。
  //
  // 两棵树重复是指它们具有相同的结构以及相同的结点值。

  // 示例 1：
  //
  //        1
  //       / \
  //      2   3
  //     /   / \
  //    4   2   4
  //       /
  //      4
  //
  //下面是两个重复的子树：
  //
  //      2
  //     /
  //    4
  //
  //  和
  //
  //    4
  //
  // 因此，你需要以列表的形式返回上述重复子树的根结点。

  // 24ms 78.87%
  class Solution {

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
      if (root == null) {
        return Collections.emptyList();
      }
      Set<String> hash = new HashSet<>();
      Map<String, TreeNode> ans = new HashMap<>();
      findDuplicateSubtrees(root, hash, ans);
      return new ArrayList<>(ans.values());
    }

    private String findDuplicateSubtrees(TreeNode root, Set<String> hash,
      Map<String, TreeNode> ans) {
      if (root == null) {
        return "*";
      }
      if (root.left == null && root.right == null) {
        String s = Integer.toString(root.val);
        if (!hash.add(s)) {
          ans.put(s, root);
        }
        return s;
      }
      String ls = findDuplicateSubtrees(root.left, hash, ans);
      String rs = findDuplicateSubtrees(root.right, hash, ans);
      String s = Integer.toString(root.val) + "#" + ls + "#" + rs;
      if (!hash.add(s)) {
        ans.put(s, root);
      }
      return s;
    }

  }

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
}
