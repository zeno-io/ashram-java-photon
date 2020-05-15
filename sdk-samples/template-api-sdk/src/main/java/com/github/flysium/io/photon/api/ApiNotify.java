package com.github.flysium.io.photon.api;

import com.github.flysium.io.photon.api.dto.ApiLogVO;
import com.github.flysium.io.photon.api.proxy.ApiReverseProxy;
import com.github.flysium.io.photon.api.service.ApiQueryService;
import com.github.flysium.io.yew.common.logger.Logger;
import com.github.flysium.io.yew.common.logger.LoggerFactory;
import com.github.flysium.io.yew.common.sequence.IDUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
   * @param apiId API编码
   * @param appId 应用ID
   * @param tenantId 租户ID
   * @param request 通知对象
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
      result = this.apiReverseProxy.reverseInvoke(apiId, appId, tenantId, request, responseClazz, log);
    }
    finally {
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
      }
      catch (RuntimeException e) {
        LOGGER.error(e);
      }
      ApiContext.remove();
    }
    return result;
  }

}
