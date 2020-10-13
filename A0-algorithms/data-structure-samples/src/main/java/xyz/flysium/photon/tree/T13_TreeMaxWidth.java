package xyz.flysium.photon.tree;

/**
 * TODO description
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
      if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
        System.out.println("Oops!");
      }
    }
    System.out.println("finish!");
  }

  private static boolean maxWidthNoMap(TreeNode head) {
    return true;
  }

  private static boolean maxWidthUseMap(TreeNode head) {
    return true;
  }

}
