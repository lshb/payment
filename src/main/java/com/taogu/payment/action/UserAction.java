package com.taogu.payment.action;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taogu.payment.dao.AppDao;
import com.taogu.payment.dao.UserDao;
import com.taogu.payment.domain.App;
import com.taogu.payment.domain.User;
import com.taogu.payment.util.ValidateUtil;

@Controller
@RequestMapping("/user")
public class UserAction extends BasicAction {

  private final static Logger log = Logger.getLogger(UserAction.class);
  @Autowired
  private UserDao userDao;
  @Autowired
  private AppDao appDao;

  @RequestMapping("/loginView")
  public ModelAndView loginView() {
    getHttpSession().removeAttribute("user");
    return getModeView("login");
  }

  @RequestMapping("/login")
  public ModelAndView login(String nick, String password) {
    if (ValidateUtil.stringsHaveEmpty(nick, password)) {
      return getLayoutModeAndView("login", "用户名、密码不能为空！");
    }
    User user = userDao.findByNick(nick);
    String pass = null;
    try {
      pass = DigestUtils.md5DigestAsHex(password.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    if (user.getPassword().equals(pass)) {
      // 保存用户信息到session
      getHttpSession().setAttribute("user", user);
      // 保存用户的app信息到session
      List<App> apps = appDao.findByUser(user.getId());
      getHttpSession().setAttribute("apps", apps);
      // 保存用户的第一个app
      App app = new App();
      if (apps != null && apps.size() > 0) {
        app = apps.get(0);
      }
      getHttpSession().setAttribute("app", app);
      return getModeView("index");
    }
    return getLayoutModeAndView("login", "密码不正确！");
  }

  @RequestMapping("/logout")
  public ModelAndView logout() {
    getHttpSession().invalidate();
    return getModeView("login");
  }

  @RequestMapping("/validateCode")
  public boolean validateCode(String phone) {
    Random r = new Random();
    int code = r.nextInt(10000);
    getHttpSession().setAttribute("validateCode", code);
    // --TODO 发送短信

    return true;
  }

  @RequestMapping("/registerView")
  public ModelAndView registerView() {
    return getModeView("register");
  }

  @PostMapping("/register")
  public ModelAndView register(String nick, String password, String rePassword, String phone, String validateCode) {
    if (ValidateUtil.stringsHaveEmpty(nick, password, rePassword, phone, validateCode)) {
      return getLayoutModeAndView("register", "注册参数不能为空！");
    }
    Long id = userDao.exit(nick);
    if (id != null) {
      return getLayoutModeAndView("register", "已经存在此用户名！");
    }
    if (!password.equals(rePassword)) {
      return getLayoutModeAndView("register", "两次密码不一致！");
    }
    // --TODO 发送短信完成后打开
    // Object code = getHttpSession().getAttribute("validateCode");
    // if (code == null || !validateCode.equals((String) code)) {
    // return getModeView("register", "校验码校验失败！");
    // }
    try {
      userDao.insert(nick, DigestUtils.md5DigestAsHex(password.getBytes("utf-8")), phone);
      return getLayoutModeAndView("login", "注册成功！");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return getLayoutModeAndView("register", e.getMessage());
    }
  }
}
