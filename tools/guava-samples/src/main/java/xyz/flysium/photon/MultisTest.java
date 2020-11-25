package xyz.flysium.photon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 有数量的集合
 *
 * @author zeno
 */
public class MultisTest {

  @Test
  public void multiset() {
    ArrayList<String> arrayList = Lists.newArrayList("a", "b", "c", "d", "a", "c");
    HashMultiset<String> multiset = HashMultiset.create(arrayList);
    multiset.elementSet().forEach(s -> System.out.println(s + ":" + multiset.count(s)));
    /*
     * result:
     * a:2
     * b:1
     * c:2
     * d:1
     */
    Assertions.assertEquals(2, multiset.count("a"));
    Assertions.assertEquals(1, multiset.count("b"));
    Assertions.assertEquals(2, multiset.count("c"));
    Assertions.assertEquals(1, multiset.count("d"));
  }


  @Test
  public void multimap() {
    HashMultimap<String, String> multimap = HashMultimap.create();
    multimap.put("狗", "大黄");
    multimap.put("狗", "旺财");
    multimap.put("猫", "加菲");
    multimap.put("猫", "汤姆");
    // [加菲, 汤姆]
    System.out.println(multimap.get("猫"));
    Assertions.assertEquals(2, multimap.get("猫").size());
    Assertions.assertTrue(multimap.get("猫").contains("加菲"));
    Assertions.assertTrue(multimap.get("猫").contains("汤姆"));
  }

}
