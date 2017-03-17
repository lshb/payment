package com.taogu.payment;

import com.taogu.payment.bean.BusinessData;

public interface Pay {

  // 支付方式标识
  public String getName();

  // 下订单
  public String unifiedOrder(BusinessData data);

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
