package xyz.flysium.photon.algorithm.hash.basic.beginner;

import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.algorithm.string.medium.T0424_LongestRepeatingCharacterReplacement;
import xyz.flysium.photon.algorithm.string.medium.T0424_LongestRepeatingCharacterReplacement_1;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0424_LongestRepeatingCharacterReplacementTest {

  @Test
  public void test() {
    T0424_LongestRepeatingCharacterReplacement.Solution solution = new T0424_LongestRepeatingCharacterReplacement.Solution();
    Assert.assertEquals(4, solution.characterReplacement("AAAA", 0));
    Assert.assertEquals(5, solution.characterReplacement("BAAAB", 2));
    Assert.assertEquals(2, solution.characterReplacement("ABCDE", 1));
    Assert.assertEquals(4, solution.characterReplacement("ABAB", 2));
    Assert.assertEquals(4, solution.characterReplacement("AABABBA", 1));
    Assert.assertEquals(7, solution.characterReplacement("ABCABCABCABC", 4));
    Assert.assertEquals(9, solution.characterReplacement("HJBBGUBBBOPBBKLJBBBB", 3));
    Assert.assertEquals(6, solution.characterReplacement(
      "IMNJJTRMJEGMSOLSCCQICIHLQIOGBJAEHQOCRAJQMBIBATGLJDTBNCPIFRDLRIJHRABBJGQAOLIKRLHDRIGERENNMJSDSSMESSTR",
      2));
  }

  @Test
  public void test1() {
    T0424_LongestRepeatingCharacterReplacement_1.Solution solution = new T0424_LongestRepeatingCharacterReplacement_1.Solution();
    Assert.assertEquals(4, solution.characterReplacement("AAAA", 0));
    Assert.assertEquals(5, solution.characterReplacement("BAAAB", 2));
    Assert.assertEquals(2, solution.characterReplacement("ABCDE", 1));
    Assert.assertEquals(4, solution.characterReplacement("ABAB", 2));
    Assert.assertEquals(4, solution.characterReplacement("AABABBA", 1));
    Assert.assertEquals(7, solution.characterReplacement("ABCABCABCABC", 4));
    Assert.assertEquals(9, solution.characterReplacement("HJBBGUBBBOPBBKLJBBBB", 3));
    Assert.assertEquals(6, solution.characterReplacement(
      "IMNJJTRMJEGMSOLSCCQICIHLQIOGBJAEHQOCRAJQMBIBATGLJDTBNCPIFRDLRIJHRABBJGQAOLIKRLHDRIGERENNMJSDSSMESSTR",
      2));
  }

//  public static void main(String[] args) {
//    String s = "IMNJJTRMJEGMSOLSCCQICIHLQIOGBJAEHQOCRAJQMBIBATGLJDTBNCPIFRDLRIJHRABBJGQAOLIKRLHDRIGERENNMJSDSSMESSTR";
//    for (int i = 0; i < s.length() - 6; i++) {
//      char[] cs = s.substring(i, i + 6).toCharArray();
//      int[] hash = new int[26];
//      for (char c : cs) {
//        hash[c - 'A']++;
//        if (hash[c - 'A'] >= 4) {
//          System.out.println(i + "," + String.valueOf(cs));
//        }
//      }
//    }
//  }

}
