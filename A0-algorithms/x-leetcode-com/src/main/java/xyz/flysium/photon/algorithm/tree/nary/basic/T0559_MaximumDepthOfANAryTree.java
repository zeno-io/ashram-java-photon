package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.ListIterator;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 559. N叉树的最大深度
 * <p>
 * https://leetcode-cn.com/problems/maximum-depth-of-n-ary-tree/
 *
 * @author zeno
 */
public interface T0559_MaximumDepthOfANAryTree {

  // 给定一个 N 叉树，找到其最大深度。
  //
  // 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。


  class Solution {

    public int maxDepth(Node root) {
      if (root == null) {
        return 0;
      }
      int maxDepthOfChildren = 0;
      if (root.children != null && !root.children.isEmpty()) {
        ListIterator<Node> it = root.children.listIterator(0);
        while (it.hasNext()) {
          Node n = it.next();
          if (n != null) {
            maxDepthOfChildren = Math.max(maxDepthOfChildren, maxDepth(n));
          }
        }
      }
      return maxDepthOfChildren + 1;
    }

  }

}
