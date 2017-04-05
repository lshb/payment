package com.taogu.payment.pay.ali;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.taogu.payment.system.Pay;

/**
 * 最后是要放到plugins文件夹中的
 * @author speedy
 *
 */
public class Alipay implements Pay {

  private AlipayClient alipayClient = null;
  private static String URL = "https://openapi.alipay.com/gateway.do";
  private static String FORMAT = "JSON";
  private static String CHARSET = "utf-8";
  private static String SIGN_TYPE = "RSA2";
  private String notify_url;
  private String return_url;
  private String appPrivateKey;
  private String alipayPublicKey;
  private String aliAppId;

  @Override
  public void init(JSONObject config) {
    this.appPrivateKey = config.getString("appPrivateKey");
    this.alipayPublicKey = config.getString("alipayPublicKey");
    this.aliAppId = config.getString("aliAppId");
    this.notify_url = config.getString("notify_url");
    this.return_url = config.getString("return_url");
    alipayClient = new DefaultAlipayClient(URL, aliAppId, appPrivateKey, FORMAT, CHARSET, alipayPublicKey, SIGN_TYPE);
  }

  // 超时时间 可空
  private static String TIMEOUT_EXPRESS = "30m";
  // 销售产品码 必填
  private static String PRODUCT_CODE = "QUICK_WAP_PAY";

  // 下单接口
  public JSONObject unifiedOrder(JSONObject data) {
    // 商户订单号，商户网站订单系统中唯一订单号，必填
    String out_trade_no = data.getString("trade_no");
    // 订单名称，必填
    String subject = data.getString("subject");
    // 付款金额，必填
    String total_amount = data.getString("amount");
    // 商品描述，可空
    String body = data.getString("body");
    /**********************/
    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
    // 调用RSA签名方式
    AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

    // 封装请求支付信息
    AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
    model.setOutTradeNo(out_trade_no);
    model.setSubject(subject);
    model.setTotalAmount(total_amount);
    model.setBody(body);
    model.setTimeoutExpress(TIMEOUT_EXPRESS);
    model.setProductCode(PRODUCT_CODE);
    alipay_request.setBizModel(model);
    // 设置异步通知地址
    alipay_request.setNotifyUrl(data.getString("notify_url"));
    // 设置同步地址
    alipay_request.setReturnUrl(data.getString("return_url"));

    // form表单生产
    String form = "";
    try {
      // 调用SDK生成表单
      form = alipayClient.pageExecute(alipay_request).getBody();
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    System.err.println("下单生成的form：" + form);
    JSONObject json = new JSONObject();
    json.put("object", form);
    return json;
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
