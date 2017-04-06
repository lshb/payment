package com.taogu.payment.action;

import java.util.Map;

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
  public ModelAndView alipay(@PathVariable("type") String type) throws Exception {
    JSONObject params = new JSONObject();
    params.put("body", "测试订单描述");
    params.put("subject", "测试订单名称");
    params.put("amount", "0.01");
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
