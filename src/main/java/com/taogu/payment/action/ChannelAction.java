package com.taogu.payment.action;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.dao.PayAccountDao;
import com.taogu.payment.domain.User;
import com.taogu.payment.system.PayClassManager;

@Controller
@RequestMapping("/channel")
public class ChannelAction extends BasicAction {

  @Autowired
  private PayAccountDao payAccountDao;

  @GetMapping("/list")
  public ModelAndView list() {
    Set<String> payTypes = PayClassManager.getPayTypes();
    ModelAndView modeView = getModeView("/channel/list");
    modeView.addObject("channels", payTypes);
    return modeView;
  }

  @GetMapping("/{type}")
  public ModelAndView channel(@PathVariable("type") String type) {
    if ("view".equals(type)) {
      return null;
    }
    ModelAndView modeView = getChannelModelAndView(type);
    User user = (User) getHttpSession().getAttribute("user");
    String config = payAccountDao.find(user.getId(), type);
    modeView.addAllObjects(JSON.parseObject(config));
    return modeView;
  }

  @PostMapping("/submit")
  public String submitChannel(@RequestParam Map<String, String> config, @RequestParam("type") String type) {
    JSONObject json = new JSONObject();
    json.putAll(config);
    User user = (User) getHttpSession().getAttribute("user");
    payAccountDao.insertOrUpdate(user.getId(), type, json.toString());
    return "redirect:"+type;
  }

  private ModelAndView getChannelModelAndView(String type) {
    ModelAndView modeView = getModeView("/channel/view");
    modeView.addObject("type", type);
    return modeView;
  }

}
