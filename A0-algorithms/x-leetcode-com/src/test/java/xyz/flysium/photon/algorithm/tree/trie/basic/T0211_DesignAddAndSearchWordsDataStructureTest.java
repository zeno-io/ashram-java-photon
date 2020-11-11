package xyz.flysium.photon.algorithm.tree.trie.basic;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import xyz.flysium.photon.ArraySupport;
import xyz.flysium.photon.algorithm.tree.trie.basic.T0211_DesignAddAndSearchWordsDataStructure.WordDictionary;

/**
 * TODO description
 *
 * @author zeno
 */
public class T0211_DesignAddAndSearchWordsDataStructureTest {

  @Test
  public void main() {
    check(
      "[\"WordDictionary\",\"addWord\",\"addWord\",\"addWord\",\"search\",\"search\",\"search\",\"search\"]",
      "[[],[\"bad\"],[\"dad\"],[\"mad\"],[\"pad\"],[\"bad\"],[\".ad\"],[\"b..\"]]",
      "[null,null,null,null,false,true,true,true]\n");

    check(
      "[\"WordDictionary\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"search\",\"search\",\"addWord\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\"]",
      "[[],[\"at\"],[\"and\"],[\"an\"],[\"add\"],[\"a\"],[\".at\"],[\"bat\"],[\".at\"],[\"an.\"],[\"a.d.\"],[\"b.\"],[\"a.d\"],[\".\"]]",
      "[null,null,null,null,null,false,false,null,true,true,false,false,true,false]");

    check(
      "[\"WordDictionary\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"addWord\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\",\"search\"]",
      "[[],[\"ran\"],[\"rune\"],[\"runner\"],[\"runs\"],[\"add\"],[\"adds\"],[\"adder\"],[\"addee\"],[\"r.n\"],[\"ru.n.e\"],[\"add\"],[\"add.\"],[\"adde.\"],[\".an.\"],[\"...s\"],[\"....e.\"],[\".......\"],[\"..n.r\"]]",
      "[null,null,null,null,null,null,null,null,null,true,false,true,true,true,false,true,true,false,false]");

  }

  private void check(String operation, String params, String expected) {
    String[] ops = ArraySupport.newStringArray(operation);
    Object[] pss = ArraySupport.newObjectArray(params);
    Object[] ess = ArraySupport.newObjectArray(expected.replace("null", "\"\""));

    WordDictionary wordDictionary = null;
    for (int i = 0; i < ops.length; i++) {
      switch (ops[i]) {
        case "WordDictionary":
          wordDictionary = new T0211_DesignAddAndSearchWordsDataStructure().new WordDictionary();
          break;
        case "addWord": {
          List pss1 = (List) pss[i];
          String str = pss1.get(0).toString();

          wordDictionary.addWord(str);
          break;
        }
        case "search": {
          List pss1 = (List) pss[i];
          String str = pss1.get(0).toString();
          boolean ex = (boolean) ess[i];
          boolean ac = wordDictionary.search(str);

          Assert.assertEquals("search(" + str + ")", ex, ac);
          break;
        }
      }
    }
  }

}
