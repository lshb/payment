package com.taogu.payment.system;

import com.alibaba.fastjson.JSONObject;

public class PayParams {

  
  private String returnUrl;
  private String notifyUrl;

  private JSONObject json;

  public String getReturnUrl() {
    return returnUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public JSONObject getJson() {
    return json;
  }

  public void setJson(JSONObject json) {
    this.json = json;
  }

}
