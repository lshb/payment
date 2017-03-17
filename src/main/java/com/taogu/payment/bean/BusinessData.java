package com.taogu.payment.bean;

import java.util.HashMap;

import com.taogu.payment.exception.BusinessDataException;

/**
 * 业务数据类
 * @author speedy
 *
 */
public class BusinessData extends HashMap<String, Object> {

  /**
   * 
   */
  private static final long serialVersionUID = 3244383540950021289L;
  
  private static String orderIdKey = "orderId";
  private static String payTypeKey = "payType";
  private static String businessTypeKey = "businessType";

  public void setBusinessName(String businessType){
    this.put(businessTypeKey, businessType);
  }
  
  public String getBusinessType() throws BusinessDataException{
    Object object = this.get(businessTypeKey);
    if (object==null) {
      throw new BusinessDataException("the businessType is nulll!");
    }
    return (String)object;
  }
  
  public void setOrderId(String orderId) {
    this.put(orderIdKey, orderId);
  }

  public String getOrderId() throws BusinessDataException {
    Object object = this.get(orderIdKey);
    if (object==null) {
      throw new BusinessDataException("the orderId is null!");
    }
    return (String)object;
  }

  public void setPayType(String payType) {
    this.put(payTypeKey, payType);
  }

  
  public String getPayType() throws BusinessDataException {
    Object object = this.get(payTypeKey);
    if (object==null) {
      throw new BusinessDataException("the PayType is null!");
    }
    return (String) object;
  }

  public static String getOrderIdKey() {
    return orderIdKey;
  }

  /**
   * 设置订单的id标识字段key
   * @param orderIdKey
   */
  public static void setOrderIdKey(String orderIdKey) {
    BusinessData.orderIdKey = orderIdKey;
  }

  public static String getPayTypeKey() {
    return payTypeKey;
  }

  /**
   * 设置订单的支付渠道key
   * @param payTypeKey
   */
  public static void setPayTypeKey(String payTypeKey) {
    BusinessData.payTypeKey = payTypeKey;
  }

}
