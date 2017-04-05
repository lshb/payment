package com.taogu.payment.pay.weixin;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.system.Pay;

public class WeiXinPay implements Pay {

  @Override
  public void init(JSONObject config) {
    // TODO Auto-generated method stub

  }

  @Override
  public Map<String, Object> unifiedOrder(JSONObject params) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String orderQuery(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String closeOrder(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean refund(String orderId) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String downloadBill() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean notifyResult() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int payId() {
    return 2;
  }

}
