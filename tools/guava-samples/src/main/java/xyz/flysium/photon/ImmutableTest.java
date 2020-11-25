package xyz.flysium.photon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 不可变的集合
 *
 * <li>使用 Guava 创建的不可变集合是拒绝 <code>null</code> 值的，因为在 Google 内部调查中，95% 的情况下都不需要放入 <code>null</code>
 * 值。</li>
 * <li>使用 JDK 提供的不可变集合创建成功后，原集合添加元素会体现在不可变集合中，而 Guava 的不可变集合不会有这个问题。</li>
 * <li>如果不可变集合的元素是引用对象，那么引用对象的属性是可以更改的。</li>
 *
 * @author zeno
 */
public class ImmutableTest {

  @Test
  public void of() {
// 创建方式1：of
    ImmutableSet<String> immutableSet = ImmutableSet.of("a", "b", "c");
    immutableSet.forEach(System.out::println);
// a
// b
// c
  }

  @Test
  public void builder() {
    // 创建方式2：builder
    ImmutableSet<String> immutableSet2 = ImmutableSet.<String>builder()
      .add("hello")
      .add(new String("未读代码"))
      .build();
    immutableSet2.forEach(System.out::println);
// hello
// 未读代码
  }

  @Test
  public void copyOf() {
// 创建方式3：从其他集合中拷贝创建
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("www.wdbyte.com");
    arrayList.add("https");
    ImmutableSet<String> immutableSet3 = ImmutableSet.copyOf(arrayList);
    immutableSet3.forEach(System.out::println);
// www.wdbyte.com
// https
  }

  @Test
  public void jdk() {
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("www.wdbyte.com");
    arrayList.add("https");
// JDK Collections 创建不可变 List
    List<String> list = Collections.unmodifiableList(arrayList);
    list.forEach(System.out::println);
    // www.wdbyte.com
    // https
    // java.lang.UnsupportedOperationException
    Assertions.assertThrows(UnsupportedOperationException.class, () -> {
      list.add("未读代码");
    });
  }

  @Test
  public void guavaVsJdk() {
    List<String> arrayList = new ArrayList<>();
    arrayList.add("a");
    arrayList.add("b");
    List<String> jdkUnmodifiableList = Collections.unmodifiableList(arrayList);
    ImmutableList<String> immutableList = ImmutableList.copyOf(arrayList);
    arrayList.add("ccc");
    jdkUnmodifiableList.forEach(System.out::println);
    // result: a b ccc
    System.out.println("-------");
    immutableList.forEach(System.out::println);
    // result: a b
    Assertions.assertEquals(3, jdkUnmodifiableList.size());
    Assertions.assertEquals(2, immutableList.size());
  }

}
