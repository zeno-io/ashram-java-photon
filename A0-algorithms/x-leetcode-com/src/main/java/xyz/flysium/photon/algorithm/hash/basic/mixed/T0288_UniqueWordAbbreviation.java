package xyz.flysium.photon.algorithm.hash.basic.mixed;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 288. 单词的唯一缩写
 * <p>
 * https://leetcode-cn.com/problems/unique-word-abbreviation/
 *
 * @author zeno
 */
public interface T0288_UniqueWordAbbreviation {

  // 一个单词的缩写需要遵循 <起始字母><中间字母数><结尾字母> 这样的格式。
  // 以下是一些单词缩写的范例：
  //
  //a) it                      --> it    (没有缩写)
  //
  //     1
  //     ↓
  //b) d|o|g                   --> d1g
  //
  //              1    1  1
  //     1---5----0----5--8
  //     ↓   ↓    ↓    ↓  ↓
  //c) i|nternationalizatio|n  --> i18n
  //
  //              1
  //     1---5----0
  //     ↓   ↓    ↓
  //d) l|ocalizatio|n          --> l10n
  //
  //请你判断单词缩写在字典中是否唯一。当单词的缩写满足下面任何一个条件是，可以认为该单词缩写是唯一的：
  //
  //    字典 dictionary 中没有任何其他单词的缩写与该单词 word 的缩写相同。
  //    字典 dictionary 中的所有缩写与该单词 word 的缩写相同的单词都与 word 相同。
  //
  //

  //   每个单词都只由小写字符组成

  // 84ms 89.26%
  class ValidWordAbbr {

    Map<String, Set<String>> hash = new HashMap<>();

    public ValidWordAbbr(String[] dictionary) {
      for (String dict : dictionary) {
        String key = abbr(dict);
        Set<String> s = hash.computeIfAbsent(key, k -> new HashSet<>());
        s.add(dict);
      }
    }

    private String abbr(String dict) {
      if (dict.length() < 3) {
        return dict;
      }
      return "" + dict.charAt(0) + (dict.length() - 2) + dict.charAt(dict.length() - 1);
    }

    public boolean isUnique(String word) {
      String abbr = abbr(word);
      Set<String> s = hash.get(abbr);
      if (s == null) {
        return true;
      }
      return s.size() == 1 && s.contains(word);
    }

  }

/**
 * Your ValidWordAbbr object will be instantiated and called as such:
 * ValidWordAbbr obj = new ValidWordAbbr(dictionary);
 * boolean param_1 = obj.isUnique(word);
 */

}
