package com.taogu.payment.system;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.taogu.payment.dao.PayAccountDao;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.pay.ali.Alipay;
import com.taogu.payment.util.ClassUtil;

@Component
public class PayManager {

  private final static Logger log = Logger.getLogger(PayManager.class);
  private HashMap<Long, Map<String, Pay>> pays = new HashMap<>();

  @Autowired
  private PayAccountDao payAccountDao;

  
  static{
    PayClassManager.addPayType(Alipay.class);
    System.err.println(PayClassManager.getPay("alipay").getName());
  }
  /**
   * 下单接口
   * @param userId 客户端id
   * @param payType 支付类型
   * @param params 订单业务数据
   * @return 订单
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws PayExeption 
   */
  public Map<String, Object> pay(long userId, String payType, JSONObject params) throws PayExeption, InstantiationException, IllegalAccessException {
    Pay pay = getPayObject(userId, payType);
    log.info("下单,userId:" + userId + ",type:" + payType + ",params:" + params);
    return pay.unifiedOrder(params);
  }

  /**
   *  获得pay实体类对象
   * @param userId
   * @param payType
   * @return
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   */
  public Pay getPayObject(long userId, String payType) throws InstantiationException, IllegalAccessException {
    Map<String, Pay> map = pays.get(userId);
    if (map != null) {
      Pay pay = map.get(payType);
      if (pay != null) {
        return pay;
      } else {
        Pay p = initPay(userId, payType);
        map.put(payType, p);
        return p;
      }
    } else {
      Map<String, Pay> ps = new HashMap<>();
      pays.put(userId, ps);
      Pay pay = initPay(userId, payType);
      ps.put(payType, pay);
      return pay;
    }
  }

  /**
   * 实例化type类型的pay对象
   * @param payType
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  private Pay initPay(long userId, String payType) throws InstantiationException, IllegalAccessException {
    Pay pay = ClassUtil.getInstance(PayClassManager.getPay(payType));
    // 从库中拿到支付类型的配置实例化对象
    String config = payAccountDao.find(userId, payType);
    JSONObject payConfig = JSONObject.parseObject(config);
    pay.init(payConfig);
    log.info("初始化支付客户端,u:" + userId + ",type:" + payType);
    return pay;
  }

  /**
   * 退费
   */
  public boolean refund(long userId, String payType, String orderId) {

    return false;
  }

}
