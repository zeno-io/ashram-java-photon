package xyz.flysium.photon;

/**
 * TODO description
 *
 * @author zeno
 */
public final class StringSupport {

  private StringSupport() {
  }

  /**
   * 对数组JSON每个元素增加 "" 双引号
   *
   * @param arrayString 数组JSON
   * @return 新的数组JSON
   */
  public static String appendStringForJSON(String arrayString) {
    arrayString = arrayString.replace("[", "[\"");
    arrayString = arrayString.replace(",", "\",\"");
    arrayString = arrayString.replace("]", "\"]");
    return arrayString;
  }

}
