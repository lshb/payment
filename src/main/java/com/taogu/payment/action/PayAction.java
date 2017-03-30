package com.taogu.payment.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.system.PayManager;

@RestController
public class PayAction {

  // 下订单
  @RequestMapping("/makePay")
  public Object makePay(String json) throws InstantiationException, IllegalAccessException, PayExeption {
    JSONObject params = new JSONObject();
    Integer appId = 0;
    String payType = "";
    return PayManager.makeOrder(appId, payType, params);
  }

  // 退款
  @RequestMapping("/refund")
  public boolean refund(String orderId) {
    return PayManager.refund(orderId);
  }

  // 支付回调
  @RequestMapping("/callBack")
  public boolean callBack() {

    return false;
  }
}
