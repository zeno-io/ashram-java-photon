package xyz.flysium.photon.algorithm.hash.basic.mixed;

/**
 * 771. 宝石与石头
 * <p>
 * https://leetcode-cn.com/problems/jewels-and-stones/
 *
 * @author zeno
 */
public interface T0771_JewelsAndStones {

  // 给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。
  // S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
  //
  // J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
  //

  // 1ms 98.57%
  class Solution {

    public int numJewelsInStones(String J, String S) {
      // ASCII
      int[] hash = new int[128];
      for (byte b : J.getBytes()) {
        hash[b] = 1;
      }
      for (char c : S.toCharArray()) {
        if (hash[c] > 0) {
          hash[c]++;
        }
      }
      int ans = 0;
      // 'A' - 'Z'
      for (int i = 65; i <= 90; i++) {
        if (hash[i] > 1) {
          ans += hash[i] - 1;
        }
      }
      // 'a' - 'z'
      for (int i = 97; i <= 122; i++) {
        if (hash[i] > 1) {
          ans += hash[i] - 1;
        }
      }
      return ans;
    }

  }

}
