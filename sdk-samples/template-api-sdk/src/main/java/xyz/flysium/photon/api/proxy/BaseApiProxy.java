/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon.api.proxy;

import com.alibaba.fastjson.JSONObject;
import com.github.flysium.io.yew.common.crypto.MAC;
import com.github.flysium.io.yew.common.crypto.SecretSpi;
import com.github.flysium.io.yew.common.crypto.support.SignatureUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import xyz.flysium.photon.api.ApiRequest;
import xyz.flysium.photon.api.dto.ApiConfiguration;
import xyz.flysium.photon.api.dto.AppSecret;
import xyz.flysium.photon.api.service.ApiQueryService;

/**
 * API抽象代理类
 *
 * @author Sven Augustus
 */
public abstract class BaseApiProxy {

  /**
   * 对JSON进行数字签名
   *
   * @param json     JSON
   * @param signType 签名方式
   * @param apiKey   安全密钥
   * @return 签名后的JSON
   */
  protected String signature(JSONObject json, String signType, String apiKey) {
    Map<String, String> params = new HashMap<>(16);
    json.forEach((key, value) -> params.putIfAbsent(key, (value == null) ? "" : value.toString()));
    params.remove(ApiRequest.PARAM_SIGN_TYPE);

    SecretSpi secretSpi = getSecretSpi(signType, apiKey);
    String signature = SignatureUtil.sign(params, apiKey, ApiRequest.PARAM_SIGN, secretSpi);

    json.put(ApiRequest.PARAM_SIGN, signature);
    return json.toJSONString();
  }

  protected static final String TARGET = "\\/";

  protected static final String REPLACEMENT = "/";

  /**
   * 签名校验
   *
   * @param json   JSON
   * @param apiKey 安全密钥
   * @return 校验成功还是失败
   */
  protected Pair<Boolean, SortedSet<String>> signatureCheck(JSONObject json, String apiKey) {
    //  签名校验
    Map<String, String> params = new HashMap<>(16);
    json.forEach((key, value) -> params.putIfAbsent(key, (value == null) ? "" : value.toString()));
    String signType = params.get(ApiRequest.PARAM_SIGN_TYPE);
    params.remove(ApiRequest.PARAM_SIGN_TYPE);

    String exceptSign = params.get(ApiRequest.PARAM_SIGN);
    SecretSpi secretSpi = getSecretSpi(signType, apiKey);

    boolean checkSign = SignatureUtil.signCheck(params, apiKey, ApiRequest.PARAM_SIGN, secretSpi);
    if (!checkSign) {
      // 针对JSON \/问题，替换/后再尝试做一次验证
      String signSourceData = SignatureUtil.getSignContent(params, apiKey, ApiRequest.PARAM_SIGN);
      if (!StringUtils.isEmpty(signSourceData) && signSourceData.contains(TARGET)) {
        String sourceData = signSourceData.replace(TARGET, REPLACEMENT);
        return Pair.of(exceptSign.equals(SignatureUtil.sign(sourceData, secretSpi)),
            new TreeSet<>(params.keySet()));
      } else {
        return Pair.of(false, new TreeSet<>(params.keySet()));
      }
    }
    return Pair.of(true, null);
  }

  /**
   * 解析异常信息
   *
   * @param e 异常
   */
  protected String asException(Throwable e) {
    String failMessage = e.getMessage();
    if (StringUtils.isBlank(failMessage)) {
      if (e instanceof NullPointerException) {
        failMessage = "NullPointerException";
      } else if (e instanceof InvocationTargetException) {
        InvocationTargetException targetException = (InvocationTargetException) e;
        if (targetException.getCause() != null) {
          failMessage = targetException.getCause().getMessage();
        } else if (targetException.getTargetException() != null) {
          failMessage = targetException.getTargetException().getMessage();
        }
      }
    }
    return (failMessage == null) ? "null" : failMessage;
  }

  protected AppSecret checkAndGetAppSecret(
      String desc, ApiQueryService apiQueryService, String appId) {
    AppSecret appSecret = apiQueryService.queryAppSecretByAppId(appId);
    if (appSecret == null) {
      throw new RuntimeException(desc + " - 缺少应用配置, appId=" + appId);
    }
    if (StringUtils.isEmpty(appSecret.getAppKey())) {
      throw new RuntimeException(desc + " - 缺少应用密钥, appId=" + appId);
    }
    return appSecret;
  }

  protected ApiConfiguration checkAndGetApiConfiguration(String desc,
      ApiQueryService apiQueryService, String appId,
      String apiId) {
    ApiConfiguration apiConfiguration = apiQueryService.queryApiConfigurationByAppId(appId, apiId);
    if (apiConfiguration == null) {
      throw new RuntimeException(desc + " - 缺少API配置, appId=" + appId + ", apiId=" + apiId);
    }
    return apiConfiguration;
  }

  private SecretSpi getSecretSpi(String signType, String apiKey) {
    if (StringUtils.isEmpty(signType)) {
      return SignatureUtil.MD5;
    }
    switch (signType) {
      case ApiRequest.SIGN_TYPE_HMAC_SHA256:
        MAC mac = new MAC();
        mac.setKey(apiKey);
        return mac;
      case ApiRequest.SIGN_TYPE_SHA256:
        return SignatureUtil.SHA256;
      default:
        return SignatureUtil.MD5;
    }
  }

}
