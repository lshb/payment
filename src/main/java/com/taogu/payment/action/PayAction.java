package com.taogu.payment.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.taogu.payment.config.Config;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.system.PayManager;
import com.taogu.payment.system.PayParams;

@RestController
@RequestMapping("/pay")
public class PayAction extends BasicAction {

  private static Logger log = Logger.getLogger(PayAction.class);
  @Autowired
  private Config config;
  @Autowired
  private PayManager payManager;

  /**
   * 下单接口
   * @param json  包含参数如下：  
   *                        userId，支付系统中的用户id
   *                        payType,用户支付类型：支付宝、微信
   *                        
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws PayExeption
   */
  @RequestMapping("/order")
  public Object order(String params) throws InstantiationException, IllegalAccessException, PayExeption {
    System.err.println("params:" + params);
    JSONObject json = JSONObject.parseObject(params);
    PayParams pp = new PayParams();
    // 必须外网可以访问
    json.put("return_url", "http://return_url.jsp");
    json.put("notify_url", "https://notify_url.jsp");
    long userId = json.getLongValue("userId");
    String payType = json.getString("payType");
    return payManager.pay(userId, payType, json);
  }

  // 退款
  @RequestMapping("/refund")
  public boolean refund(long appId, String payType, String orderId) {
    return payManager.refund(appId, payType, orderId);
  }

  // 支付异步回调
  @RequestMapping("/callBack")
  public String callBack(HttpServletRequest request) {
    // 获取支付宝POST过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
      params.put(name, valueStr);
    }
    log.info("callBack:" + params);
    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号
    // String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
    // "UTF-8");
    // // 支付宝交易号
    //
    // String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
    // "UTF-8");
    //
    // // 交易状态
    // String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),
    // "UTF-8");
    //
    // // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
    // // 计算得出通知验证结果
    // // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String
    // // charset, String sign_type)
    // boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,
    // AlipayConfig.CHARSET, "RSA2");
    //
    // if (verify_result) {// 验证成功
    // //////////////////////////////////////////////////////////////////////////////////////////
    // // 请在这里加上商户的业务逻辑程序代码
    //
    // // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
    //
    // if (trade_status.equals("TRADE_FINISHED")) {
    // // 判断该笔订单是否在商户网站中已经做过处理
    // // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
    // // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
    // // 如果有做过处理，不执行商户的业务程序
    //
    // // 注意：
    // // 如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
    // // 如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
    // } else if (trade_status.equals("TRADE_SUCCESS")) {
    // // 判断该笔订单是否在商户网站中已经做过处理
    // // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
    // // 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
    // // 如果有做过处理，不执行商户的业务程序
    //
    // // 注意：
    // // 如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
    // }
    //
    // // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
    //
    // //////////////////////////////////////////////////////////////////////////////////////////
    // } else {// 验证失败
    //
    // }
    return "kajsdlkfjlk.jsp";
  }

  // 支付同步通知
  @RequestMapping("/returnResult")
  public String returnResult(HttpServletRequest request) {
    // 获取支付宝GET过来反馈信息
    Map<String, String> params = new HashMap<String, String>();
    Map requestParams = request.getParameterMap();
    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
      String name = (String) iter.next();
      String[] values = (String[]) requestParams.get(name);
      String valueStr = "";
      for (int i = 0; i < values.length; i++) {
        valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
      }
      // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
      params.put(name, valueStr);
    }
    log.info("returnResult:" + params);
    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    // 商户订单号

    // String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
    // "UTF-8");
    //
    // // 支付宝交易号
    //
    // String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
    // "UTF-8");

    // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
    // 计算得出通知验证结果
    // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String
    // charset, String sign_type)
    //
    // boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY,
    // AlipayConfig.CHARSET, "RSA2");
    //
    // if (verify_result) {// 验证成功
    // //////////////////////////////////////////////////////////////////////////////////////////
    // // 请在这里加上商户的业务逻辑程序代码
    // // 该页面可做页面美工编辑
    //
    // // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
    // //////////////////////////////////////////////////////////////////////////////////////////
    // } else {
    // // 该页面可做页面美工编辑
    // }
    return "kajsdlkfjlk.jsp";
  }
}
