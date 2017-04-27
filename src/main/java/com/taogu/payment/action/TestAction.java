package com.taogu.payment.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.domain.User;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.system.PayManager;

@Controller
@RequestMapping("/test")
public class TestAction extends BasicAction {

  @Autowired
  private PayManager pay;

  @RequestMapping("/{type}")
  public ModelAndView pay(@PathVariable("type") String type) throws Exception {
    JSONObject params = new JSONObject();
    if ("alipay".equals(type)) {
      params.put("body", "测试订单描述");
      params.put("subject", "测试订单名称");
      params.put("amount", "0.01");
    } else if ("weixinpay".equals(type)) {
      params.put("body", "ceshiyixia");
      params.put("total_fee", 1);
      params.put("spbill_create_ip", "192.168.0.1");
//      params.put("trade_type", "JSAPI");
      params.put("trade_type", "MWEB");
//      params.put("openId", "12312312312");
      params.put("notify_url", "https://www.91taogu.com");
    }
    Object obj = null;
    User user = (User) getHttpSession().getAttribute("user");
    try {
      obj = pay.pay(user.getId(), type, params);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (PayExeption e) {
      e.printStackTrace();
    }
    ModelAndView modeView = new ModelAndView("test");
    modeView.addObject("form", obj);
    return modeView;
  }
}
