package com.taogu.payment.pay.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.system.Pay;
import com.taogu.payment.util.EncryptUtil;
import com.taogu.payment.util.SignUtil;
import com.taogu.payment.util.StringUtil;

public class WeiXinPay implements Pay {

  private static String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
  private static String appId;
  private static String mchId; // 商户号
  private static String apiSecret;
  private static String notify_url = "https://www.91taogu.com";

  @Override
  public void init(JSONObject config) {
    this.appId = config.getString("appId");
    this.mchId = config.getString("mchId");
    this.apiSecret=config.getString("apiSecret");
  }

  @Override
  public Object unifiedOrder(JSONObject params) {
    String tradeNo = params.getString("trade_no"); // 订单号
    String body = params.getString("body"); // 商品描述
    String totalFee = params.getString("total_fee");// 金额
    String billIp = params.getString("spbill_create_ip");// 终端ip
    String notifyUrl = params.getString("notify_url");
    String tradeType = params.getString("trade_type");// 交易类型
    String openId = params.getString("openId");// 公众号openid

    Map<String, String> map = new HashMap<>();
    map.put("appid", appId);
    map.put("mch_id", mchId);
    String nonceStr = StringUtil.getRandomString(30);
    map.put("nonce_str", nonceStr);
    map.put("body", body);
    map.put("out_trade_no", tradeNo);
    map.put("total_fee", totalFee);
    map.put("spbill_create_ip", billIp);
    map.put("notify_url", notifyUrl);
    map.put("trade_type", tradeType);
    map.put("openid", openId);

    String ps = SignUtil.formatUrlMap(map, false, true);
    System.err.println("ps:"+ps);
    try {
      String sign = EncryptUtil.md5(ps+"&key="+apiSecret).toUpperCase();
      System.err.println("sign:"+sign);
      StringBuffer sb = new StringBuffer("<xml>");
      sb.append("<appid>" + appId + "</appid>");
      sb.append("<body>" + body + "</body>");
      sb.append("<mch_id>" + mchId + "</mch_id>");
      sb.append("<nonce_str>" + nonceStr + "</nonce_str>");
      sb.append("<notify_url>" + notifyUrl + "</notify_url>");
      if (tradeType.equals("JSAPI")) {
        sb.append("<openid>" + openId + "</openid>");
      }
      sb.append("<out_trade_no>" + tradeNo + "</out_trade_no>");
      sb.append("<spbill_create_ip>" + billIp + "</spbill_create_ip>");
      sb.append("<total_fee>" + totalFee + "</total_fee>");
      sb.append("<trade_type>" + tradeType + "</trade_type>");
      sb.append("<sign>" + sign + "</sign>");
      sb.append("</xml>");
      System.err.println(sb.toString());
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost post = new HttpPost(URL);
      post.setEntity(new StringEntity(sb.toString()));
      CloseableHttpResponse response = client.execute(post);
      InputStream content = response.getEntity().getContent();
      BufferedReader br = new BufferedReader(new InputStreamReader(content));
      String line = null;
      while ((line=br.readLine())!=null) {
        System.err.println(line);
      }
    } catch (NoSuchAlgorithmException | IOException e1) {
      e1.printStackTrace();
    }
    return null;
  }

  @Override
  public String orderQuery(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String closeOrder(String orderId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean refund(String orderId) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String downloadBill() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean notifyResult() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int payId() {
    return 2;
  }

  public static void main(String[] args) {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost post = new HttpPost("http://www.baidu.com");
    try {
      CloseableHttpResponse response = client.execute(post);
      System.err.println(response.getEntity().getContent());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
