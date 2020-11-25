package xyz.flysium.photon;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * 字符串操作
 *
 * @author zeno
 */
public class StringsTest {

  @Test
  public void joinUsingJdk() {
    // JDK 方式一
    ArrayList<String> list = Lists.newArrayList("a", "b", "c", null);
    String join = String.join(",", list);
    // a,b,c,null
    System.out.println(join);
    // JDK 方式二
    String result = list.stream().collect(Collectors.joining(","));
    // a,b,c,null
    System.out.println(result);
    // JDK 方式三
    StringJoiner stringJoiner = new StringJoiner(",");
    list.forEach(stringJoiner::add);
    // a,b,c,null
    System.out.println(stringJoiner.toString());
  }

  @Test
  public void joinUsingGuava() {
    ArrayList<String> list = Lists.newArrayList("a", "b", "c", null);
    String join = Joiner.on(",").skipNulls().join(list);
    // a,b,c
    System.out.println(join);

    String join1 = Joiner.on(",").useForNull("空值").join("旺财", "汤姆", "杰瑞", null);
    // 旺财,汤姆,杰瑞,空值
    System.out.println(join1);
  }

  @Test
  public void splitUsingJdk() {
    String str = ",a,,b,";
    String[] splitArr = str.split(",");
    Arrays.stream(splitArr).forEach(System.out::println);
    System.out.println("------");
    /*
     *
     * a
     *
     * b
     * ------
     */
  }

  @Test
  public void splitUsingGuava() {
    String str = ",a ,,b ,";
    Iterable<String> split = Splitter.on(",")
      .omitEmptyStrings() // 忽略空值
      .trimResults() // 过滤结果中的空白
      .split(str);
    split.forEach(System.out::println);
  }

}
