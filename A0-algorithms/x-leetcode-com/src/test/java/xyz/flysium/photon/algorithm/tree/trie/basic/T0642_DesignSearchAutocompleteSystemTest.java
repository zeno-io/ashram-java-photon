package xyz.flysium.photon.algorithm.tree.trie.basic;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.tree.trie.basic.T0642_DesignSearchAutocompleteSystem.AutocompleteSystem;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0642_DesignSearchAutocompleteSystemTest {

  @Test
  public void test() {
    check("[\"AutocompleteSystem\",\"input\",\"input\",\"input\",\"input\"]",
      "[[[\"i love you\",\"island\",\"iroman\",\"i love leetcode\"],[5,3,2,2]],[\"i\"],[\" \"],[\"a\"],[\"#\"]]",
      "[null,[\"i love you\",\"island\",\"i love leetcode\"],[\"i love you\",\"i love leetcode\"],[],[]]");

    check(
      "[\"AutocompleteSystem\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\",\"input\"]"
      ,
      "[[[\"i love you\",\"island\",\"iroman\",\"i love leetcode\"],[5,3,2,2]],"
        + "[\"i\"],[\" \"],[\"a\"],[\"#\"],"
        + "[\"i\"],[\" \"],[\"a\"],[\"#\"],"
        + "[\"i\"],[\" \"],[\"a\"],[\"#\"]]"
      ,
      "[null,"
        + "[\"i love you\",\"island\",\"i love leetcode\"],"
        + "[\"i love you\",\"i love leetcode\"],"
        + "[],"
        + "[],"
        + "[\"i love you\",\"island\",\"i love leetcode\"],"
        + "[\"i love you\",\"i love leetcode\",\"i a\"],"
        + "[\"i a\"],"
        + "[],"
        + "[\"i love you\",\"island\",\"i a\"],"
        + "[\"i love you\",\"i a\",\"i love leetcode\"],"
        + "[\"i a\"],"
        + "[]]");
  }

  private void check(String operation, String params, String expected) {
//    ["AutocompleteSystem","input","input","input","input","input","input","input","input","input","input","input","input"]
//    [[["i love you","island","iroman","i love leetcode"],[5,3,2,2]],["i"],[" "],["a"],["#"],["i"],[" "],["a"],["#"],["i"],[" "],["a"],["#"]]
//    [null,["i love you","island","i love leetcode"],["i love you","i love leetcode"],[],[],["i love you","island","i love leetcode"],["i love you","i love leetcode","i a"],["i a"],[],["i love you","island","i a"],["i love you","i a","i love leetcode"],["i a"],[]]
    String[] ops = ArraySupport.newStringArray(operation);
    Object[] pss = ArraySupport.newObjectArray(params);
    Object[] ess = ArraySupport.newObjectArray(expected.replace("null", "\"\""));

    AutocompleteSystem autocompleteSystem = null;
    for (int i = 0; i < ops.length; i++) {
      if (ops[i].equals("AutocompleteSystem")) {
        List pss1 = (List) pss[i];
        String[] sentences = (String[]) ((List) pss1.get(0)).toArray(new String[0]);
        List l = (List) pss1.get(1);
        int[] times = new int[l.size()];
        for (int j = 0; j < l.size(); j++) {
          times[j] = (Integer) l.get(j);
        }
        autocompleteSystem = new T0642_DesignSearchAutocompleteSystem().new AutocompleteSystem(
          sentences, times);
      } else if (ops[i].equals("input")) {
        List pss1 = (List) pss[i];
        char c = pss1.get(0).toString().charAt(0);

        List ess1 = (List) ess[i];
        List<String> actuals = autocompleteSystem.input(c);

        Assert.assertArrayEquals("idx=" + i + " input(" + c + ")",
          ess1.toArray(new Object[0]),
          actuals.toArray(new String[0]));
      }
    }
  }

}
