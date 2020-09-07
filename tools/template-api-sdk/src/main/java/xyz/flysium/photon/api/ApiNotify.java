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

import com.github.flysium.io.yew.common.logger.Logger;
import com.github.flysium.io.yew.common.logger.LoggerFactory;
import com.github.flysium.io.yew.common.sequence.IDUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import xyz.flysium.photon.api.dto.ApiLogVO;
import xyz.flysium.photon.api.proxy.ApiReverseProxy;
import xyz.flysium.photon.api.service.ApiQueryService;

/**
 * API反向调用代理类
 *
 * @author Sven Augustus
 */
public class ApiNotify<I extends ExtendReverseApiRequest, O extends ReverseApiResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiNotify.class);

  private final ApiReverseProxy<I, O> apiReverseProxy;

  private final ApiLogger apiLogger;

  public ApiNotify(ApiQueryService queryService) {
    this.apiReverseProxy = new ApiReverseProxy<>(queryService);
    this.apiLogger = ApiLogger.getApiLogger();
  }

  /**
   * 反向调用或通知，并返回结果
   *
   * @param apiId         API编码
   * @param appId         应用ID
   * @param tenantId      租户ID
   * @param request       通知对象
   * @param responseClazz 响应对象的类
   */
  public O reverseInvoke(
      String apiId, String appId, String tenantId, I request, Class<O> responseClazz) {
    LOGGER.debug("API反向调用或通知：通知参数=" + request);
    // @formatter:off
    ApiContext context = ApiContext.get();
    ApiLogVO log = ApiLogVO.builder()
        .logId(String.valueOf(IDUtils.nextId()))
        .apiType("1")
        .protocolType(context.getProtocolType())
        .appId(appId)
        .tenantId(tenantId)
        .apiCode(apiId)
        .clientIp(context.getClientIp())
        .serverIp(context.getServerIp())
        .build();
    // @formatter:on
    log.setStartTime(new Date());

    O result = null;
    try {
      result = this.apiReverseProxy
          .reverseInvoke(apiId, appId, tenantId, request, responseClazz, log);
    } finally {
      LOGGER.debug("API反向调用或通知：返回参数=" + result);
      log.setEndTime(new Date());
      log.setLogDate(log.getEndTime());
      log.setProcessMsec(log.getEndTime().getTime() - log.getStartTime().getTime());
      LocalDateTime startTime = log.getStartTime().toInstant().atZone(ZoneId.systemDefault())
          .toLocalDateTime();
      log.setLogYear(startTime.getYear());
      log.setLogMonth(startTime.getMonthValue());
      log.setLogDay(startTime.getDayOfMonth());
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
