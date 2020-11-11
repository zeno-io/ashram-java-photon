package xyz.flysium.photon.algorithm.queueandstack.stack;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.algorithm.queueandstack.stack.basic.U0394_DecodeString;
import xyz.flysium.photon.algorithm.queueandstack.stack.basic.U0394_DecodeString_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class U0394_DecodeStringTest {

  @Test
  public void test() {
    U0394_DecodeString.Solution solution = new U0394_DecodeString.Solution();
//    Assert.assertEquals("abcabccdcdcdef", solution.decodeString("2[abc]3[cd]ef"));
//    Assert.assertEquals("aaabcbc", solution.decodeString("3[a]2[bc]"));
    Assert.assertEquals("accaccacc", solution.decodeString("3[a2[c]]"));
    Assert.assertEquals("abccdcdcdxyz", solution.decodeString("abc3[cd]xyz"));
  }

  @Test
  public void test1() {
    U0394_DecodeString_1.Solution solution = new U0394_DecodeString_1.Solution();
    Assert.assertEquals("abcabccdcdcdef", solution.decodeString("2[abc]3[cd]ef"));
    Assert.assertEquals("aaabcbc", solution.decodeString("3[a]2[bc]"));
    Assert.assertEquals("accaccacc", solution.decodeString("3[a2[c]]"));
    Assert.assertEquals("abccdcdcdxyz", solution.decodeString("abc3[cd]xyz"));
  }

}
