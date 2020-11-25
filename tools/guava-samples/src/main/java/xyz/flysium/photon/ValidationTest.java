package xyz.flysium.photon;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 数据校验
 *
 * @author zeno
 */
public class ValidationTest {

  @Test
  public void checkNotNull() {
    String param = "未读代码";
    String name = Preconditions.checkNotNull(param);
    // 未读代码
    System.out.println(name);

    Assertions.assertThrows(NullPointerException.class, () -> {
      // NullPointerException
      String param2 = null;
      String name2 = Preconditions.checkNotNull(param2);
      System.out.println(name2);
    });
  }

  @Test
  public void checkArgument() {
    String param = "www.wdbyte.com2";
    String wdbyte = "www.wdbyte.com";
// java.lang.IllegalArgumentException: [www.wdbyte.com2] 404 NOT FOUND
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      Preconditions.checkArgument(wdbyte.equals(param), "[%s] 404 NOT FOUND", param);
    });
  }

  @Test
  public void checkElementIndex() {
    // Guava 中快速创建ArrayList
    List<String> list = Lists.newArrayList("a", "b", "c", "d");
    // java.lang.IndexOutOfBoundsException: index (5) must be less than size (4)
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
      // 开始校验
      int index = Preconditions.checkElementIndex(5, list.size());
    });
  }

}
