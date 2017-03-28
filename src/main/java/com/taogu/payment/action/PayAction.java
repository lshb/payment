package com.taogu.payment.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taogu.payment.PayManager;
import com.taogu.payment.bean.PayParams;
import com.taogu.payment.exception.PayExeption;

@RestController
public class PayAction {

  // 下订单
  @RequestMapping("/makePay")
  public Object makePay(String json) throws InstantiationException, IllegalAccessException, PayExeption {
    PayParams params = new PayParams();
    return PayManager.makeOrder(params);
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
