package com.taogu.payment.plugins;

import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.taogu.payment.Pay;
import com.taogu.payment.bean.PayParams;

public class Alipay implements Pay {

  private AlipayClient alipayClient = null;

  public Alipay(String appId) {
    String url = "https://openapi.alipay.com/gateway.do";
    String appPrivateKey = "";
    String alipayPublicKey = "";
    String format = "json";
    String charset = "utf-8";
    String signType = "RSA2";
    alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);
  }

  public Map<String, Object> unifiedOrder(PayParams data) {
    // TODO 下单接口 alipay.trade.wap.pay
    AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

    try {
      alipayClient.pageExecute(request);
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String orderQuery(String orderId) {
    // TODO 查询订单
    return null;
  }

  public String closeOrder(String orderId) {
    // TODO 关闭订单
    return null;
  }

  public boolean refund(String orderId) {
    // TODO 退费接口
    return false;
  }

  public String downloadBill() {
    // TODO 下载订单账单
    return null;
  }

  public boolean notifyResult() {
    // TODO 通知结果
    return false;
  }

}
