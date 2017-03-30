package com.taogu.payment.system;

public class PayParams {

  private String payType;
  private String payOrderId;
  private String desc;

  public String getPayType() {
    return payType;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public String getPayOrderId() {
    return payOrderId;
  }

  public void setPayOrderId(String payOrderId) {
    this.payOrderId = payOrderId;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
