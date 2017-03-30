package com.taogu.payment.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.util.ClassUtil;

public class PayManager {

  private final static Logger log = Logger.getLogger(PayManager.class);
  private static HashMap<Integer, Map<String, Pay>> pays = new HashMap<>();

  /**
   * 下单接口
   * @param appId 客户端id
   * @param payType 支付类型
   * @param params 订单业务数据
   * @return 订单
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws PayExeption 
   */
  public static Map<String, Object> makeOrder(Integer appId, String payType, JSONObject params)
      throws PayExeption, InstantiationException, IllegalAccessException {
    Pay pay = getPayObject(appId, payType);
    return pay.unifiedOrder(params);
  }

  /**
   *  获得pay实体类对象
   * @param appId
   * @return
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   */
  public static Pay getPayObject(Integer appId, String payType) throws InstantiationException, IllegalAccessException {
    Map<String, Pay> map = pays.get(appId);
    if (map != null) {
      Pay pay = map.get(payType);
      if (pay != null) {
        return pay;
      } else {
        Pay p = initPay(appId, payType);
        map.put(payType, p);
        return p;
      }
    } else {
      Map<String, Pay> ps = new HashMap<>();
      pays.put(appId, ps);
      Pay pay = initPay(appId, payType);
      ps.put(payType, pay);
      return pay;
    }
  }

  /**
   * 实例化type类型的pay对象
   * @param type
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  private static Pay initPay(Integer appId, String type) throws InstantiationException, IllegalAccessException {
    Pay pay = ClassUtil.getInstance(PayClassManager.getPay(type));
    // --TODO 从库中拿到支付类型的配置实例化对象
    // PayConfig config = payConfigs.get(appId).get(type);
    // pay.init(config);
    return pay;
  }

  /**
   * 退费
   * @param orderId 退费的订单id
   * @return
   */
  public static boolean refund(String orderId) {

    return false;
  }

  
}
