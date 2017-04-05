package com.taogu.payment.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.system.PayManager;

@Controller
@RequestMapping("/test")
public class TestAction extends BasicAction {

  @Autowired
  private PayManager pay;

  @RequestMapping("/alipay")
  public ModelAndView alipayView() {
    return getModeView("alipay");
  }

  @RequestMapping("/alipayPay")
  public String alipayPay(String orderName, String orderDesc, String orderMoney) throws Exception {
    System.err.println(orderName);
    System.err.println(orderDesc);
    System.err.println(orderMoney);
    JSONObject params = new JSONObject();
    params.put("body", orderDesc);
    params.put("subject", orderName);
    params.put("amount", orderMoney);
    Map<String, Object> json = null;
    try {
      json = pay.pay(1, "alipay", params);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (PayExeption e) {
      e.printStackTrace();
    }
    return (String) json.get("object");
  }

  @RequestMapping("/weixin")
  public ModelAndView weixinView() {
    return getModeView("weixin");
  }

  @RequestMapping("/weixinPay")
  public ModelAndView weixinPay() {

    return getModeView("weixin");
  }
}
