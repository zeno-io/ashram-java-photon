/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium.photon.crypto.support;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import xyz.flysium.photon.crypto.SecretSpi;
import xyz.flysium.photon.crypto.impl.MD5;
import xyz.flysium.photon.crypto.impl.SHA256;


/**
 * Signature Tool
 *
 * @author Sven Augustus
 * @version 1.0
 */
public class SignatureUtil {

  // sign、signType 参数不参与签名
  public static final String PARAM_SIGN = "sign";
  public static final String PARAM_SIGN_TYPE = "signType";

  private SignatureUtil() {
  }

  /**
   * 签名 (MD5)
   *
   * @param params 参数，不包括签名
   * @param apiKey 密钥
   * @return 签名字符串
   */
  public static String sign(Map<String, String> params, String apiKey) {
    return sign(params, apiKey, PARAM_SIGN, null);
  }

  /**
   * 签名 (MD5)
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @return 签名字符串
   */
  public static String sign(Map<String, String> params, String apiKey,
    String paramSignKey) {
    return sign(params, apiKey, paramSignKey, null, CryptoUtil.DEFAULT_CHARSET);
  }

  /**
   * 签名
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @param secretSpi    信息摘要，不可逆加密算法
   * @return 签名字符串
   */
  public static String sign(Map<String, String> params, String apiKey,
    String paramSignKey,
    SecretSpi secretSpi) {
    return sign(params, apiKey, paramSignKey, secretSpi, CryptoUtil.DEFAULT_CHARSET);
  }

  /**
   * 签名
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @param secretSpi    信息摘要，不可逆加密算法
   * @param charset      编码
   * @return 签名字符串
   */
  public static String sign(Map<String, String> params, String apiKey,
    String paramSignKey,
    SecretSpi secretSpi,
    Charset charset) {
    String content = getSignContent(params, apiKey, paramSignKey);
    return sign(content, secretSpi, charset);
  }


  /**
   * 验签 (MD5)
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @return 是否验证通过
   */
  public static boolean signCheck(Map<String, String> params, String apiKey,
    String paramSignKey) {
    return signCheck(params, apiKey, paramSignKey, null, CryptoUtil.DEFAULT_CHARSET);
  }

  /**
   * 验签
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @param secretSpi    信息摘要，不可逆加密算法
   * @return 是否验证通过
   */
  public static boolean signCheck(Map<String, String> params, String apiKey,
    String paramSignKey,
    SecretSpi secretSpi) {
    return signCheck(params, apiKey, paramSignKey, secretSpi, CryptoUtil.DEFAULT_CHARSET);
  }

  /**
   * 验签
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @param secretSpi    信息摘要，不可逆加密算法
   * @param charset      编码
   * @return 是否验证通过
   */
  public static boolean signCheck(Map<String, String> params, String apiKey,
    String paramSignKey,
    SecretSpi secretSpi,
    Charset charset) {
    String content = getSignContent(params, apiKey, paramSignKey);
    String sign = sign(content, secretSpi, charset);
    return sign.equals(params.get(paramSignKey));
  }

  /**
   * 获取签名字符串内容
   *
   * @param params       参数
   * @param apiKey       密钥
   * @param paramSignKey 参数中代表签名的键
   * @return 签名字符串内容
   */
  public static String getSignContent(Map<String, String> params, String apiKey,
    String paramSignKey) {
    StringBuilder content = new StringBuilder();
    List<String> keys = new ArrayList<>(params.keySet());
    Collections.sort(keys);
    int index = 0;
    for (String key : keys) {
      String value = params.get(key);
      if (paramSignKey != null && paramSignKey.equals(key)) {
        continue;
      }
      if (PARAM_SIGN_TYPE.equals(key)) {
        continue;
      }
      if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
        content.append((index == 0 ? "" : "&")).append(key).append("=").append(value);
        index++;
      }
    }
    return content.toString().concat("&key=").concat(apiKey);
  }


  /**
   * 签名
   *
   * @param src       源明文字符串
   * @param secretSpi 算法
   * @return 签名字符串内容
   */
  public static String sign(String src, SecretSpi secretSpi) {
    return sign(src, secretSpi, CryptoUtil.DEFAULT_CHARSET);
  }

  /**
   * 签名
   *
   * @param src       源明文字符串
   * @param secretSpi 算法
   * @param charset   编码
   * @return 签名字符串内容
   */
  public static String sign(String src, SecretSpi secretSpi, Charset charset) {
    if (charset == null) {
      charset = CryptoUtil.DEFAULT_CHARSET;
    }
    if (secretSpi == null) {
      secretSpi = SignatureUtil.MD5;
    }
    return CryptoUtil.hex(secretSpi.encrypt(src.getBytes(charset)));
  }

  public static final SecretSpi MD5 = new MD5();
  public static final SecretSpi SHA256 = new SHA256();

}
