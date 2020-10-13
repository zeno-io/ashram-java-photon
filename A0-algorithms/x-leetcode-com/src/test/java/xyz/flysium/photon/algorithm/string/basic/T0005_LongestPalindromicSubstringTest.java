package xyz.flysium.photon.algorithm.string.basic;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0005_LongestPalindromicSubstringTest {

  @Test
  public void test() {
    T0005_LongestPalindromicSubstring.Solution solution = new T0005_LongestPalindromicSubstring.Solution();

//    Assert.assertEquals("a", solution.longestPalindrome("a"));
    Assert.assertEquals("a", solution.longestPalindrome("ac"));
    Assert.assertEquals("bb", solution.longestPalindrome("bb"));
    Assert.assertEquals("bab", solution.longestPalindrome("babad"));
  }

  @Test
  public void test1() {
    T0005_LongestPalindromicSubstring_1.Solution solution = new T0005_LongestPalindromicSubstring_1.Solution();

    Assert.assertEquals("a", solution.longestPalindrome("a"));
    Assert.assertEquals("a", solution.longestPalindrome("ac"));
    Assert.assertEquals("bb", solution.longestPalindrome("bb"));
    Assert.assertEquals("bab", solution.longestPalindrome("babad"));
  }

  @Test
  public void test2() {
    T0005_LongestPalindromicSubstring_2.Solution solution = new T0005_LongestPalindromicSubstring_2.Solution();

    Assert.assertEquals("a", solution.longestPalindrome("a"));
    Assert.assertEquals("a", solution.longestPalindrome("ac"));
    Assert.assertEquals("bb", solution.longestPalindrome("bb"));
    Assert.assertEquals("bab", solution.longestPalindrome("babad"));
  }
}
