package com.taogu.payment.system;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface Pay {

  //利用支付帐号配置页面的信息进行支付客户端初始化
  public void init(JSONObject config);

  // 下订单
  public Map<String, Object> unifiedOrder(JSONObject params);

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
