package xyz.flysium.photon.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 求二叉树最宽的层有多少个节点
 *
 * @author zeno
 */
public class T13_TreeMaxWidth {

  public static void main(String[] args) {
    int maxLevel = 10;
    int maxValue = 100;
    int testTimes = 1000000;
    for (int i = 0; i < testTimes; i++) {
      TreeNode head = TreeSupport.generateRandomBinarySearchTree(maxLevel, maxValue);
      if (maxWidthWithinMap(head) != maxWidthWithoutMap(head)) {
        System.out.println("-> Wrong algorithm !!!");
      }
    }
    System.out.println("Finish!");
  }

  private static int maxWidthWithoutMap(TreeNode head) {
    if (head == null) {
      return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    int maxWidth = Integer.MIN_VALUE;

    queue.offer(head);
    while (!queue.isEmpty()) {
      int sz = queue.size();
      maxWidth = Math.max(sz, maxWidth);
      for (int i = 0; i < sz; i++) {
        TreeNode node = queue.poll();
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
    }

    return maxWidth;
  }

  private static int maxWidthWithinMap(TreeNode head) {
    if (head == null) {
      return 0;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(head);
    // key 在 哪一层，value
    HashMap<TreeNode, Integer> levelMap = new HashMap<>();
    levelMap.put(head, 1);
    // 当前你正在统计哪一层的宽度
    int curLevel = 1;
    // 当前层curLevel层，宽度目前是多少
    int curLevelNodes = 0;
    int max = 0;
    while (!queue.isEmpty()) {
      TreeNode cur = queue.poll();
      int curNodeLevel = levelMap.get(cur);
      if (cur.left != null) {
        levelMap.put(cur.left, curNodeLevel + 1);
        queue.add(cur.left);
      }
      if (cur.right != null) {
        levelMap.put(cur.right, curNodeLevel + 1);
        queue.add(cur.right);
      }
      if (curNodeLevel == curLevel) {
        curLevelNodes++;
      } else {
        max = Math.max(max, curLevelNodes);
        curLevel++;
        curLevelNodes = 1;
      }
    }
    max = Math.max(max, curLevelNodes);
    return max;
  }

}
