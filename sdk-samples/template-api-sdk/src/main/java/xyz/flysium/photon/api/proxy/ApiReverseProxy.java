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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.flysium.io.yew.common.logger.Logger;
import com.github.flysium.io.yew.common.logger.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import xyz.flysium.photon.api.ApiRequest;
import xyz.flysium.photon.api.ExtendReverseApiRequest;
import xyz.flysium.photon.api.ReverseApiRequest;
import xyz.flysium.photon.api.ReverseApiResponse;
import xyz.flysium.photon.api.dto.ApiConfiguration;
import xyz.flysium.photon.api.dto.ApiLogVO;
import xyz.flysium.photon.api.dto.AppSecret;
import xyz.flysium.photon.api.service.ApiQueryService;

/**
 * API反向调用或通知代理类
 *
 * @author Sven Augustus
 */
public class ApiReverseProxy<I extends ExtendReverseApiRequest, O extends ReverseApiResponse> extends
    BaseApiProxy {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiReverseProxy.class);

  private final RestTemplate restTemplate;

  private final ApiQueryService apiQueryService;

  public ApiReverseProxy(ApiQueryService apiQueryService) {
    this.restTemplate = new RestTemplate(getClientHttpRequestFactory());
    this.apiQueryService = apiQueryService;
  }

  // Override timeouts in request factory
  private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    // Connect timeout
    clientHttpRequestFactory.setConnectTimeout(5000);
    // Read timeout
    clientHttpRequestFactory.setReadTimeout(15000);
    return clientHttpRequestFactory;
  }

  /**
   * 反向调用或通知，并返回结果
   *
   * @param apiId         API编码
   * @param appId         应用ID
   * @param tenantId      租户ID
   * @param request       通知对象
   * @param responseClazz 响应对象的类
   * @param log           日志对象
   * @return 结果
   */
  @SuppressWarnings("PMD.AvoidCatchingThrowable")
  public O reverseInvoke(
      String apiId, String appId, String tenantId, I request, Class<O> responseClazz,
      ApiLogVO log) {
    ApiReverseContext<I> context;
    String responseJsonString = null;

    O response;
    try {
      context = getContext(apiId, appId, tenantId, request, log);
      // 合并结果
      JSONObject requestJson = (JSONObject) JSON.toJSON(context.getNotifyApiRequest());
      if (request != null) {
        JSONObject extendRequestJson = (JSONObject) JSON.toJSON(request);
        extendRequestJson.forEach(requestJson::putIfAbsent);
      }
      // 对数据进行签名
      super.signature(requestJson, ApiRequest.SIGN_TYPE_SHA256, context.getApiKey());
      // 通知
      String requestJsonString = requestJson.toJSONString();
      log.setRequestMsg(requestJsonString);
      LOGGER.debug("API反向调用或通知：通知报文=" + requestJsonString);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(requestJsonString, headers);
      ResponseEntity<String> responseEntity = restTemplate
          .postForEntity(context.getUrl(), entity, String.class);
      responseJsonString = responseEntity.getBody();
    } catch (Throwable e) {
      responseJsonString =
          "{\"returnCode\":\"FAIL\",\"returnMsg\":\"" + "API反向调用或通知失败: " + e.getMessage() + "\"}";
      LOGGER.error("API反向调用或通知失败, logId = " + log.getLogId() + " : " + e.getMessage(), e);
      throw e;
    } finally {
      if (responseJsonString == null) {
        responseJsonString = "{\"returnCode\":\"FAIL\"}";
      }
      log.setResponseMsg(responseJsonString);
      response = JSON.parseObject(responseJsonString, responseClazz);
      log.setResultCode(response.getReturnCode());
      log.setResultMsg(response.getReturnMsg());
    }
    // 返回
    return response;
  }

  private ApiReverseContext<I> getContext(String apiId, String appId, String tenantId, I request,
      ApiLogVO log) {
    //  获取密钥，并根据API编码、应用ID获取通知URL
    AppSecret appSecret = super.checkAndGetAppSecret("API反向调用或通知错误", apiQueryService, appId);
    ApiConfiguration apiConfiguration = super
        .checkAndGetApiConfiguration("API反向调用或通知错误", apiQueryService, appId, apiId);
    log.setApiSpecId(apiConfiguration.getApiSpecId());
    log.setApiInstId(apiConfiguration.getApiInstId());
    log.setUrl(apiConfiguration.getUrl());
    // @formatter:off
    ReverseApiRequest notifyApiRequest = ReverseApiRequest.builder()
        .appId(appId)
        .tenantId(tenantId)
        .transactionId(log.getLogId())
        .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
        .nonceStr(RandomStringUtils.randomAlphanumeric(32))
        .build();
    // @formatter:on
    return ApiReverseContext.<I>builder().notifyApiRequest(notifyApiRequest).request(request)
        .apiKey(appSecret.getAppKey()).url(apiConfiguration.getUrl()).build();
  }

}
