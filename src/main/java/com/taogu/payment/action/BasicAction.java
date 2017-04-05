package com.taogu.payment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

public class BasicAction {

  private final static String LAYOUT_VIEW = "fragments/layout";
  private final static String CONTENT_VIEW = "view";
  private final static String VIEW_MESSAGE = "message";

  public HttpSession getHttpSession() {
    return getHttpRequest().getSession();
  }

  public HttpServletRequest getHttpRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return attrs.getRequest();
  }

  public ModelAndView getModeView(String view) {
    ModelAndView mav = new ModelAndView(LAYOUT_VIEW);
    mav.addObject(CONTENT_VIEW, view);
    return mav;
  }

  /**
   * 封装modeView
   * @param view 要在内容中显示的视图
   * @param message 视图中显示的信息
   * @return
   */
  public ModelAndView getModeView(String view, String message) {
    ModelAndView mav = new ModelAndView(LAYOUT_VIEW);
    mav.addObject(CONTENT_VIEW, view);
    mav.addObject(VIEW_MESSAGE, message);
    return mav;
  }

  
  
  
}
