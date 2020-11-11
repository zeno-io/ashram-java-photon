package xyz.flysium.photon.algorithm.hash.basic.mixed;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0003_LengthOf_LongestSubstringTest {

  @Test
  public void test() {
    T0003_LengthOfLongestSubstring.Solution solution = new T0003_LengthOfLongestSubstring.Solution();

    Assert.assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    Assert.assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    Assert.assertEquals(3, solution.lengthOfLongestSubstring("pwwkew"));
    Assert.assertEquals(8, solution.lengthOfLongestSubstring("ttmxubssvjkxcht"));
  }

}
