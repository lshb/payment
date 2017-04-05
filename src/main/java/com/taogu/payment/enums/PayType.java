package com.taogu.payment.enums;

public enum PayType {

  ;
  private int payId;
  private String payName;
  
  private PayType(int payId, String payName) {
    this.payId = payId;
    this.payName = payName;
  }
  
}
