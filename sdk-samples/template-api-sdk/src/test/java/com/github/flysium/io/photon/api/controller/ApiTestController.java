/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        TestApiResponse resp = new TestApiResponse();
        resp.setAccessToken("xxxxx");
        return resp;
      }
    };
    return proxy.invoke("api-test-invoke", invoker, request);
  }

  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestApiRequest extends ApiRequest {

    private String userType;

    private String userId;

    public String getUserType() {
      return userType;
    }

    public void setUserType(String userType) {
      this.userType = userType;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }
  }

  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestApiResponse extends ExtendApiResponse {

    private String accessToken;

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }
  }

  /*
    示例：调用或通知外系统
    */
  @RequestMapping("/notify")
  public ReverseApiResponse testNotify() {
    ApiNotify<TestNotifyApiRequest, ReverseApiResponse> apiNotify = new ApiNotify<>(queryService);

    TestNotifyApiRequest request = new TestNotifyApiRequest();
    request.setStaffId("100098");
    request.setStatus("online");
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

  @SuppressWarnings("PMD.UnusedPrivateField")
  static class TestNotifyApiRequest extends ExtendReverseApiRequest {

    private String staffId;

    private String status;

    public String getStaffId() {
      return staffId;
    }

    public void setStaffId(String staffId) {
      this.staffId = staffId;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }

}
