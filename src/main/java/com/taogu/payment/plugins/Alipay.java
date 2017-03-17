package com.taogu.payment.plugins;

import com.taogu.payment.Pay;
import com.taogu.payment.bean.BusinessData;

public class Alipay implements Pay{

  public String getName() {
    return "alipay";
  }

  public String unifiedOrder(BusinessData data) {
    // TODO Auto-generated method stub
    return null;
  }

  public String orderQuery(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  public String closeOrder(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean refund(String orderId) {
    // TODO Auto-generated method stub
    return false;
  }

  public String downloadBill() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean notifyResult() {
    // TODO Auto-generated method stub
    return false;
  }

}
