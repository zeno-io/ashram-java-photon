package xyz.flysium.photon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * TODO description
 *
 * @author zeno
 */
public final class ArraySupport {

  private ArraySupport() {
  }

  /**
   * 随机生成一个随机长度和随机值的数组
   *
   * @param minSize  最少个数（包含）
   * @param maxSize  最多个数（不包含）
   * @param minValue 最小值（包含）
   * @param maxValue 最大值（不包含）
   * @return 数组
   */
  public static int[] generateRandomArray(int minSize, int maxSize, int minValue, int maxValue) {
    minSize = Math.max(minSize, 2);
    maxSize = Math.max(minSize, maxSize);
    maxValue = Math.max(minValue, maxValue);

    int size = RandomSupport.randomValue(minSize, maxSize);
    int[] array = new int[size];
    for (int i = 0; i < array.length; i++) {
      array[i] = RandomSupport.randomValue(minValue, maxValue);
    }
    return array;
  }


  /**
   * 依据JOSN格式生成一个数组
   *
   * @param json JOSN格式
   * @return 数组
   */
  public static int[] newArray(String json) {
    return CODEC.deserialize(json, int[].class);
  }

  /**
   * 依据JOSN格式生成一个字符串数组
   *
   * @param arrayString JOSN格式
   * @return 字符串数组
   */
  public static String[] newStringArray(String arrayString) {
    return CODEC.deserialize(arrayString, String[].class);
  }

  /**
   * 依据JOSN格式生成一个二维数组
   *
   * @param json JOSN格式
   * @return 二维数组
   */
  public static int[][] newTwoDimensionalArray(String json) {
    return CODEC.deserialize(json, int[][].class);
  }

  /**
   * 依据JOSN格式生成一个二维字符数组
   *
   * @param json JOSN格式
   * @return 二维字符数组
   */
  public static char[][] toTwoDimensionalCharArray(String json) {
    return CODEC.deserialize(json, char[][].class);
  }

  /**
   * 依据JOSN格式生成一个二维嵌套字符串列表
   *
   * @param json JOSN格式
   * @return 二维嵌套字符串列表
   */
  public static List<List<String>> toStringListList(String json) {
    return CODEC.deserialize(json, List.class);
  }

  /**
   * 从开始到结尾（0.1.2..n-1）遍历数组
   *
   * @param arr 数组
   * @return 遍历结果
   */
  public static String toString(int[] arr) {
    return CODEC.serialize(arr);
  }

  /**
   * 从开始到结尾（0.1.2..n-1）遍历数组
   *
   * @param arr 数组
   * @return 遍历结果
   */
  public static String toString(String[] arr) {
    return CODEC.serialize(arr);
  }

  /**
   * 从开始到结尾遍历二维数组
   *
   * @param arr 二维数组
   * @return 遍历结果
   */
  public static String toString(int[][] arr) {
    return CODEC.serialize(arr);
  }

  /**
   * 列表转换为一个数组
   *
   * @param l 列表
   * @return 数组
   */
  public static int[] toArray(List<Integer> l) {
    int[] ans = new int[l.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = l.get(i);
    }
    return ans;
  }

  /**
   * 列表转换为一个排序的数组
   *
   * @param l 列表
   * @return 排序的数组
   */
  public static Object[] toSortArray(List<?> l) {
    Object[] a = l.toArray(new Object[0]);
    Arrays.sort(a);
    return a;
  }

  /**
   * 二维嵌套字符串列表转换为一个排序的列表数组
   *
   * @param l 二维嵌套字符串列表
   * @return 排序的列表数组
   */
  public static List[] toSortListArray(List<List<String>> l) {
    List[] a = l.toArray(new List[0]);
    Arrays.sort(a, new Comparator<List>() {
      @Override
      public int compare(List o1, List o2) {
        return Integer.compare(o1.size(), o2.size());
      }
    });
    return a;
  }

  /**
   * 考虑顺序，判断两个数组是否相等
   *
   * @param a 数组1
   * @param b 数组2
   * @return 是否相等
   */
  public static boolean equals(int[] a, int[] b) {
    if (a == null) {
      return b == null;
    }
    if (b == null) {
      return false;
    }
    if (a.length != b.length) {
      return false;
    }
    return equals(a, 0, b, 0, a.length);
  }

  /**
   * 考虑顺序，判断两个数组的指定部分是否相等
   *
   * @param a      数组1
   * @param b      数组2
   * @param aStart 数组1开始的索引
   * @param bStart 数组2开始的索引
   * @param length 判断的长度
   * @return 是否相等
   */
  public static boolean equals(int[] a, int aStart, int[] b, int bStart, int length) {
    if (a == null) {
      return b == null;
    }
    if (b == null) {
      return false;
    }
    for (int i = 0; i < length; i++) {
      if (a[aStart + i] != b[bStart + i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * 不考虑顺序，判断两个二维嵌套字符串列表是否相等
   *
   * @param a 二维嵌套字符串列表1
   * @param b 二维嵌套字符串列表2
   * @return 是否相等
   */
  public static boolean equalsNotSequentialStringListList(List<List<String>> a,
    List<List<String>> b) {
    if (a.size() != b.size()) {
      return false;
    }
    final int len = a.size();
    List<List<String>> al = new ArrayList<>(a);
    List<List<String>> bl = new ArrayList<>(b);

    for (int i = 0; i < len; i++) {
      Iterator<List<String>> it = bl.iterator();
      while (it.hasNext()) {
        List<String> l = it.next();
        if (equalsNotSequentialStringList(al.get(i), l)) {
          it.remove();
        }
      }
    }
    return bl.isEmpty();
  }

  /**
   * 不考虑顺序，判断两个字符串列表是否相等
   *
   * @param a 字符串列表1
   * @param b 字符串列表2
   * @return 是否相等
   */
  public static boolean equalsNotSequentialStringList(List<String> a, List<String> b) {
    if (a.size() != b.size()) {
      return false;
    }
    final int len = a.size();
    String[] as = a.toArray(new String[0]);
    String[] bs = b.toArray(new String[0]);
    Arrays.sort(as);
    Arrays.sort(bs);
    return Arrays.equals(as, bs);
  }

  private static final Codec CODEC = new Codec();

  static class Codec {

    private static final ObjectMapper mapper = new ObjectMapper();

    // Encodes a tree to a single string.
    public String serialize(int[] arr) {
      try {
        return mapper.writeValueAsString(arr);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return "[]";
    }

    // Encodes a tree to a single string.
    public String serialize(int[][] arr) {
      try {
        return mapper.writeValueAsString(arr);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return "[]";
    }

    // Encodes a tree to a single string.
    public String serialize(String[] arr) {
      try {
        return mapper.writeValueAsString(arr);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return "[]";
    }

    // Decodes your encoded data to tree.
    public <T> T deserialize(String json, Class<T> clazz) {
      try {
        return mapper.readValue(json, clazz);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    }

  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(newArray("[1,1,1]")));
  }

}
