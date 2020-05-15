package com.github.flysium.io.photon.api.controller;

import com.github.flysium.io.photon.api.ApiInvoker;
import com.github.flysium.io.photon.api.ApiNotify;
import com.github.flysium.io.photon.api.ApiProxy;
import com.github.flysium.io.photon.api.ApiRequest;
import com.github.flysium.io.photon.api.ExtendApiResponse;
import com.github.flysium.io.photon.api.ExtendReverseApiRequest;
import com.github.flysium.io.photon.api.ReverseApiResponse;
import com.github.flysium.io.photon.api.service.ApiQueryService;
import com.github.flysium.io.photon.api.service.ApiQueryServiceImpl;
import com.github.flysium.io.yew.common.logger.Logger;
import com.github.flysium.io.yew.common.logger.LoggerFactory;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API 测试
 *
 * @author Sven Augustus
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ApiTestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestController.class);

  private final ApiQueryService queryService = new ApiQueryServiceImpl();

  @GetMapping("/")
  public String home() {
    return "Hello, Welcome to Spring MVC.";
  }

  /* 测试报文：
    {
      "appId": "ctgd678efh567hg6787",
      "tenantId": "ctg_hn",
      "timestamp": "202002181459",
      "transactionId": "1009660380201506130728806387",
      "nonceStr": "C380BEC2BFD727A4B6845133519F3AD6",
      "sign": "7e139ffa4ad62be0b46b3deae6577ffa258f4126d9b444b6e1e77481ab285f41",
      "signType": "SHA256",
      "userType": "staff"
      "userId": "100098"
    }
   */
  @PostMapping("/test")
  public String test(@RequestBody TestApiRequest request) {
    ApiProxy<TestApiRequest, TestApiResponse> proxy = new ApiProxy<>(queryService);
    ApiInvoker<TestApiRequest, TestApiResponse> invoker = new ApiInvoker<TestApiRequest, TestApiResponse>() {

      @Override
      public TestApiResponse invoke(TestApiRequest request) {
        return TestApiResponse.builder().accessToken("xxxxx").build();
      }
    };
    return proxy.invoke("api-test-invoke", invoker, request);
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestApiRequest extends ApiRequest {

    private String userType;

    private String userId;
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestApiResponse extends ExtendApiResponse {

    private String accessToken;
  }

  /*
    示例：调用或通知外系统
    */
  @RequestMapping("/notify")
  public ReverseApiResponse testNotify() {
    ApiNotify<TestNotifyApiRequest, ReverseApiResponse> apiNotify = new ApiNotify<>(queryService);

    TestNotifyApiRequest request = TestNotifyApiRequest.builder().staffId("100098").status("online")
        .build();
    return apiNotify.reverseInvoke("test", "ctgd678efh567hg6787", "ctg_hn",
        request, ReverseApiResponse.class);
  }

  @PostMapping("/mock")
  public String mock(@RequestBody Map<String, Object> params) {
    return "{\n"
        + "  \"returnCode\": \"SUCCESS\",\n"
        + "  \"returnMsg\": \"处理成功\"\n"
        + "}";
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestNotifyApiRequest extends ExtendReverseApiRequest {

    private String staffId;

    private String status;
  }

}
