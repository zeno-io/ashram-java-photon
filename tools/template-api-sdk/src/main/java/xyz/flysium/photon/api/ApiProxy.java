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

package xyz.flysium.photon.api;

import com.alibaba.fastjson.JSONObject;
import com.github.flysium.io.yew.common.logger.Logger;
import com.github.flysium.io.yew.common.logger.LoggerFactory;
import com.github.flysium.io.yew.common.sequence.IDUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;
import xyz.flysium.photon.api.dto.ApiLogVO;
import xyz.flysium.photon.api.proxy.ApiInvokeProxy;
import xyz.flysium.photon.api.service.ApiQueryService;

/**
 * API调用代理类
 *
 * @author Sven Augustus
 */
public class ApiProxy<I extends ApiRequest, O extends ExtendApiResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiProxy.class);

  private final ApiInvokeProxy<I, O> apiInvokeProxy;

  private final ApiLogger apiLogger;

  public ApiProxy(ApiQueryService queryService) {
    this.apiInvokeProxy = new ApiInvokeProxy<>(queryService);
    this.apiLogger = ApiLogger.getApiLogger();
  }

  /**
   * API调用，并返回JSON格式的响应报文
   *
   * @param apiId         API编码
   * @param apiInvoker    API业务实现类
   * @param requestString 请求对象字符串
   * @param requestClazz  请求对象的类
   * @return JSON格式的响应报文
   */
  public String invoke(String apiId, ApiInvoker<I, O> apiInvoker, String requestString,
      Class<I> requestClazz) {
    return invoke((log) -> {
      LOGGER.debug("API调用：请求报文=" + requestString);
      log.setRequestMsg(requestString);
      log.setApiCode(apiId);
      I request = JSONObject.parseObject(requestString, requestClazz);
      log.setAppId(request.getAppId());
      log.setTenantId(request.getTenantId());
      return this.apiInvokeProxy.invoke(apiId, apiInvoker, request, log);
    });
  }

  /**
   * API调用，并返回JSON格式的响应报文
   *
   * @param apiId      API编码
   * @param apiInvoker API业务实现类
   * @param request    请求对象
   * @return JSON格式的响应报文
   */
  public String invoke(String apiId, ApiInvoker<I, O> apiInvoker, I request) {
    return invoke((log) -> {
      LOGGER.debug("API调用：请求报文=" + request);
      log.setApiCode(apiId);
      log.setAppId(request.getAppId());
      log.setTenantId(request.getTenantId());
      return this.apiInvokeProxy.invoke(apiId, apiInvoker, request, log);
    });
  }

  /**
   * API调用，并返回JSON格式的响应报文
   *
   * @param invoker API业务实现类
   * @return JSON格式的响应报文
   */
  private String invoke(Function<ApiLogVO, String> invoker) {
    ApiContext context = ApiContext.get();
    // @formatter:off
    ApiLogVO log = ApiLogVO.builder()
        .logId(String.valueOf(IDUtils.nextId()))
        .apiType("0")
        .protocolType(context.getProtocolType())
        .url(context.getUrl())
        .clientIp(context.getClientIp())
        .serverIp(context.getServerIp())
        .build();
    // @formatter:on
    log.setStartTime(new Date());

    String result = null;
    try {
      result = invoker.apply(log);
    } finally {
      LOGGER.debug("API调用：响应报文=" + result);
      log.setEndTime(new Date());
      log.setLogDate(log.getEndTime());
      log.setProcessMsec(log.getEndTime().getTime() - log.getStartTime().getTime());
      LocalDateTime startTime = log.getStartTime().toInstant().atZone(ZoneId.systemDefault())
          .toLocalDateTime();
      log.setLogYear(startTime.getYear());
      log.setLogMonth(startTime.getMonthValue());
      log.setLogDay(startTime.getDayOfMonth());
      log.setResponseMsg(result);
      try {
        // 写日志
        apiLogger.logger(log);
      } catch (RuntimeException e) {
        LOGGER.error(e);
      }
      ApiContext.remove();
    }
    return result;
  }

}
