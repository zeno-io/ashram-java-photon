package com.github.flysium.io.photon.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通知对象
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public final class ReverseApiRequest implements java.io.Serializable {

  private static final long serialVersionUID = -3046643706900808987L;

  private String returnCode;

  private String returnMsg;

  private String appId;

  private String tenantId;

  private String transactionId;

  private String timestamp;

  private String nonceStr;

  private String sign;

  public static ReverseApiRequest success() {
    return success(ReverseApiRequest.builder().build());
  }

  public static ReverseApiRequest success(ReverseApiRequest response) {
    response.setReturnCode(ApiInvoker.SUCCESS);
    response.setReturnMsg("处理成功");
    return response;
  }
}
