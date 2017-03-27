package com.taogu.payment;

import java.util.Map;

import com.taogu.payment.bean.PayParams;

public interface Pay {

  // 下订单
  public Map<String, Object> unifiedOrder(PayParams params);

  // 查询订单
  public String orderQuery(String orderId);

  // 关闭订单
  public String closeOrder(String orderId);

  // 申请退款
  public boolean refund(String orderId);

  // 下载对账单
  public String downloadBill();

  // 支付结果通知
  public boolean notifyResult();
}
