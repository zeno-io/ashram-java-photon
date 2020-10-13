package xyz.flysium.photon.algorithm.tree.binary;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import xyz.flysium.photon.tree.TreeNode;

/**
 * 297. 二叉树的序列化与反序列化
 * <p>
 * https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/
 *
 * @author zeno
 */
public interface W0297_SerializeAndDeserializeBinaryTree {

  //序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
  //  同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。
  //
  //请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列 / 反序列化算法执行逻辑，
  //  你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。

  // 层序遍历 24ms
  public class Codec {

    static final String NULL_STR = "#";
    static final String SPLIT_STR = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
      if (root == null) {
        return NULL_STR;
      }
      List<Integer> ans = new LinkedList<>();
      Deque<TreeNode> q = new LinkedList<>();
      TreeNode node = null;

      q.offerLast(root);
      ans.add(root.val);
      while (!q.isEmpty()) {
        int sz = q.size();
        for (int x = 0; x < sz; x++) {
          node = q.pollFirst();
          if (node.left != null) {
            q.offerLast(node.left);
            ans.add(node.left.val);
          } else {
            ans.add(null);
          }
          if (node.right != null) {
            q.offerLast(node.right);
            ans.add(node.right.val);
          } else {
            ans.add(null);
          }
        }
      }
      StringBuilder buf = new StringBuilder();
      String pre = "";
      for (Integer e : ans) {
        buf.append(pre).append((e == null) ? NULL_STR : e);
        pre = SPLIT_STR;
      }

      return buf.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
      if (NULL_STR.equals(data)) {
        return null;
      }
      Deque<TreeNode> list = new LinkedList<>();
      String[] ss = data.split(SPLIT_STR);
      for (String s : ss) {
        if (NULL_STR.equals(s)) {
          list.offerLast(null);
        } else {
          list.offerLast(new TreeNode(Integer.parseInt(s)));
        }
      }
      TreeNode root = list.pollFirst();
      Deque<TreeNode> q = new LinkedList<>();
      TreeNode node = null;

      q.offerLast(root);
      while (!q.isEmpty()) {
        int sz = q.size();
        for (int x = 0; x < sz; x++) {
          node = q.pollFirst();
          node.left = list.pollFirst();
          node.right = list.pollFirst();
          if (node.left != null) {
            q.offerLast(node.left);
          }
          if (node.right != null) {
            q.offerLast(node.right);
          }
        }
      }
      return root;
    }

  }

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
}
