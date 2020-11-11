package xyz.flysium.photon.algorithm.tree.nary.basic;

import java.util.LinkedList;
import java.util.Stack;
import xyz.flysium.photon.algorithm.tree.nary.Node;

/**
 * 428. 序列化和反序列化 N 叉树
 * <p>
 * https://leetcode-cn.com/problems/serialize-and-deserialize-n-ary-tree/
 *
 * @author zeno
 */
public interface T0428_SerializeAndDeserializeNAryTree {

  // 序列化是指将一个数据结构转化为位序列的过程，因此可以将其存储在文件中或内存缓冲区中，以便稍后在相同或不同的计算机环境中恢复结构。
  //
  //设计一个序列化和反序列化 N 叉树的算法。一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。序列化 / 反序列化算法的算法实现没有限制。你只需要保证 N 叉树可以被序列化为一个字符串并且该字符串可以被反序列化成原树结构即可。


  class Codec {

    public static final String SPLIT_STR = " ";
    public static final String CHILDREN_START = "[";
    public static final String CHILDREN_END = "]";

    // Encodes a tree to a single string.
    public String serialize(Node root) {
      StringBuilder buf = new StringBuilder();
      if (root == null) {
        return buf.toString();
      }
      encode(root, buf);
      return buf.toString();
    }

    private void encode(Node node, StringBuilder sb) {
      if (node == null) {
        return;
      }
      sb.append(node.val);
      sb.append(SPLIT_STR);

      boolean hasChildren = !node.children.isEmpty();

      // only append "[ ]" when the node has children
      if (hasChildren) {
        sb.append(CHILDREN_START).append(SPLIT_STR);
      }
      for (int i = 0; i < node.children.size(); i++) {
        Node children = node.children.get(i);
        encode(children, sb);
        if (i == node.children.size() - 1) {
          sb.deleteCharAt(sb.length() - 1);
        }
      }
      if (hasChildren) {
        sb.append(SPLIT_STR).append(CHILDREN_END).append(SPLIT_STR);
      }
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
      if (data.isEmpty()) {
        return null;
      }
      String[] strs = data.split(SPLIT_STR);
      Stack<Node> stack = new Stack<Node>();
      Node root = null;
      Node cur = null;

      for (String str : strs) {
        if (CHILDREN_START.equals(str)) {
          stack.push(cur);
        } else if (CHILDREN_END.equals(str)) {
          stack.pop();
        } else {
          Node node = new Node(Integer.parseInt(str));
          node.children = new LinkedList<Node>();
          if (root == null) {
            root = node;
          } else {
            Node parent = stack.peek();
            parent.children.add(node);
          }
          cur = node;
        }
      }
      return root;
    }

  }

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

}
