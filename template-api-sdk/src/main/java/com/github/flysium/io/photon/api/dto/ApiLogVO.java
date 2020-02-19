package com.github.flysium.io.photon.api.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API 日志
 *
 * @author Sven Augustus
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ApiLogVO implements java.io.Serializable {

  private static final long serialVersionUID = 9208526405084561213L;

  private String logId;

  private String apiType;

  private String protocolType;

  private String appId;

  private String tenantId;

  private String apiSpecId;

  private String apiCode;

  private String apiInstId;

  private String url;

  private String clientIp;

  private String serverIp;

  private String requestMsg;

  private String responseMsg;

  private Date startTime;

  private Date endTime;

  private Long processMsec;

  private String resultCode;

  private String resultMsg;

  private Date logDate;

  private Integer logYear;

  private Integer logMonth;

  private Integer logDay;

}
