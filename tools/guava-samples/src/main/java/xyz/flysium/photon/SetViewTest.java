package xyz.flysium.photon;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 集合交集并集差集
 *
 * @author zeno
 */
public class SetViewTest {

  @Test
  public void intersection() {
    Set<String> newHashSet1 = Sets.newHashSet("a", "a", "b", "c");
    Set<String> newHashSet2 = Sets.newHashSet("b", "b", "c", "d");

// 交集
    SetView<String> intersectionSet = Sets.intersection(newHashSet1, newHashSet2);
    System.out.println(intersectionSet); // [b, c]
    Assertions.assertEquals(2, intersectionSet.size());
    Assertions.assertFalse(intersectionSet.contains("a"));
    Assertions.assertTrue(intersectionSet.contains("b"));
    Assertions.assertTrue(intersectionSet.contains("c"));
    Assertions.assertFalse(intersectionSet.contains("d"));
  }

  @Test
  public void union() {
    Set<String> newHashSet1 = Sets.newHashSet("a", "a", "b", "c");
    Set<String> newHashSet2 = Sets.newHashSet("b", "b", "c", "d");

// 并集
    SetView<String> unionSet = Sets.union(newHashSet1, newHashSet2);
    System.out.println(unionSet); // [a, b, c, d]
    Assertions.assertEquals(4, unionSet.size());
    Assertions.assertTrue(unionSet.contains("a"));
    Assertions.assertTrue(unionSet.contains("b"));
    Assertions.assertTrue(unionSet.contains("c"));
    Assertions.assertTrue(unionSet.contains("d"));
  }

  @Test
  public void difference() {
    Set<String> newHashSet1 = Sets.newHashSet("a", "a", "b", "c");
    Set<String> newHashSet2 = Sets.newHashSet("b", "b", "c", "d");

// newHashSet1 中存在，newHashSet2 中不存在
    SetView<String> setView = Sets.difference(newHashSet1, newHashSet2);
    System.out.println(setView); // [a]
    Assertions.assertEquals(1, setView.size());
    Assertions.assertEquals("a", setView.iterator().next());
  }

}
