package xyz.flysium.photon.jianzhioffer.datastructure.easy;

/**
 * 剑指 Offer 58 - II. 左旋转字符串
 * <p>
 * https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof
 *
 * @author zeno
 */
public interface J0058_2_ReverseLeftWords {
  // 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。
  // 请定义一个函数实现字符串左旋转操作的功能。
  //
  // 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
  //

  // 执行用时：0 ms, 在所有 Java 提交中击败了100.00% 的用户
  class Solution {

    public String reverseLeftWords(String s, int n) {
      if (s.length() == 0) {
        return s;
      }
      int t = n % s.length();
      if (t == 0) {
        return s;
      }
      final int length = s.length();
      return s.substring(t, length)
        + s.substring(0, t);
    }

  }

}
