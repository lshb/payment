package com.taogu.payment.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeAction extends BasicAction{

  
  @RequestMapping("/")
  public ModelAndView home(){
    return getModeView("index");
  }
}
