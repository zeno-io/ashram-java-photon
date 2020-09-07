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

package xyz.flysium.photon.api.dto;

import java.util.Date;

/**
 * API 日志
 *
 * @author Sven Augustus
 */
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

  public String getLogId() {
    return logId;
  }

  public void setLogId(String logId) {
    this.logId = logId;
  }

  public String getApiType() {
    return apiType;
  }

  public void setApiType(String apiType) {
    this.apiType = apiType;
  }

  public String getProtocolType() {
    return protocolType;
  }

  public void setProtocolType(String protocolType) {
    this.protocolType = protocolType;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getApiSpecId() {
    return apiSpecId;
  }

  public void setApiSpecId(String apiSpecId) {
    this.apiSpecId = apiSpecId;
  }

  public String getApiCode() {
    return apiCode;
  }

  public void setApiCode(String apiCode) {
    this.apiCode = apiCode;
  }

  public String getApiInstId() {
    return apiInstId;
  }

  public void setApiInstId(String apiInstId) {
    this.apiInstId = apiInstId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public String getRequestMsg() {
    return requestMsg;
  }

  public void setRequestMsg(String requestMsg) {
    this.requestMsg = requestMsg;
  }

  public String getResponseMsg() {
    return responseMsg;
  }

  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Long getProcessMsec() {
    return processMsec;
  }

  public void setProcessMsec(Long processMsec) {
    this.processMsec = processMsec;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMsg() {
    return resultMsg;
  }

  public void setResultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
  }

  public Date getLogDate() {
    return logDate;
  }

  public void setLogDate(Date logDate) {
    this.logDate = logDate;
  }

  public Integer getLogYear() {
    return logYear;
  }

  public void setLogYear(Integer logYear) {
    this.logYear = logYear;
  }

  public Integer getLogMonth() {
    return logMonth;
  }

  public void setLogMonth(Integer logMonth) {
    this.logMonth = logMonth;
  }

  public Integer getLogDay() {
    return logDay;
  }

  public void setLogDay(Integer logDay) {
    this.logDay = logDay;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private final ApiLogVO apiLogVO;

    private Builder() {
      apiLogVO = new ApiLogVO();
    }

    public Builder logId(String logId) {
      apiLogVO.setLogId(logId);
      return this;
    }

    public Builder apiType(String apiType) {
      apiLogVO.setApiType(apiType);
      return this;
    }

    public Builder protocolType(String protocolType) {
      apiLogVO.setProtocolType(protocolType);
      return this;
    }

    public Builder appId(String appId) {
      apiLogVO.setAppId(appId);
      return this;
    }

    public Builder tenantId(String tenantId) {
      apiLogVO.setTenantId(tenantId);
      return this;
    }

    public Builder apiSpecId(String apiSpecId) {
      apiLogVO.setApiSpecId(apiSpecId);
      return this;
    }

    public Builder apiCode(String apiCode) {
      apiLogVO.setApiCode(apiCode);
      return this;
    }

    public Builder apiInstId(String apiInstId) {
      apiLogVO.setApiInstId(apiInstId);
      return this;
    }

    public Builder url(String url) {
      apiLogVO.setUrl(url);
      return this;
    }

    public Builder clientIp(String clientIp) {
      apiLogVO.setClientIp(clientIp);
      return this;
    }

    public Builder serverIp(String serverIp) {
      apiLogVO.setServerIp(serverIp);
      return this;
    }

    public Builder requestMsg(String requestMsg) {
      apiLogVO.setRequestMsg(requestMsg);
      return this;
    }

    public Builder responseMsg(String responseMsg) {
      apiLogVO.setResponseMsg(responseMsg);
      return this;
    }

    public Builder startTime(Date startTime) {
      apiLogVO.setStartTime(startTime);
      return this;
    }

    public Builder endTime(Date endTime) {
      apiLogVO.setEndTime(endTime);
      return this;
    }

    public Builder processMsec(Long processMsec) {
      apiLogVO.setProcessMsec(processMsec);
      return this;
    }

    public Builder resultCode(String resultCode) {
      apiLogVO.setResultCode(resultCode);
      return this;
    }

    public Builder resultMsg(String resultMsg) {
      apiLogVO.setResultMsg(resultMsg);
      return this;
    }

    public Builder logDate(Date logDate) {
      apiLogVO.setLogDate(logDate);
      return this;
    }

    public Builder logYear(Integer logYear) {
      apiLogVO.setLogYear(logYear);
      return this;
    }

    public Builder logMonth(Integer logMonth) {
      apiLogVO.setLogMonth(logMonth);
      return this;
    }

    public Builder logDay(Integer logDay) {
      apiLogVO.setLogDay(logDay);
      return this;
    }

    public ApiLogVO build() {
      return apiLogVO;
    }
  }
}
