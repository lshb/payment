package com.taogu.payment.system;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

public class PayClassManager {

  private final static Logger log = Logger.getLogger(PayClassManager.class);
  private static HashMap<String, Class<Pay>> payMap = new HashMap<String, Class<Pay>>();

  public static Class<Pay> getPay(String type) {
    return payMap.get(type);
  }

  /**
   * 添加一种支付类型
   * @param pay
   * @return
   */
  public static Class<Pay> addPayType(Class<Pay> pay) {
    log.info("add one payType ,the type is" + pay.getName());
    return payMap.put(pay.getName().toLowerCase(), pay);
  }

  /**
   * 获取所有支持的支付类型
   * @return
   */
  public static Set<String> getPayTypes() {
    return payMap.keySet();
  }

}
