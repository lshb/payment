package com.taogu.payment.system;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

public class PayClassManager {

  private final static Logger log = Logger.getLogger(PayClassManager.class);
  private static HashMap<String, Class<? extends Pay>> payMap = new HashMap<>();

  public static Class<? extends Pay> getPay(String type) {
    return payMap.get(type);
  }

  /**
   * 添加一种支付类型
   * @param pay
   * @return
   */
  public static Class<? extends Pay> addPayType(Class<? extends Pay> pay) {
    log.info("add one payType ,the type is " + pay.getSimpleName());
    return payMap.put(pay.getSimpleName().toLowerCase(), pay);
  }

  /**
   * 获取所有支持的支付类型
   * @return
   */
  public static Set<String> getPayTypes() {
    return payMap.keySet();
  }

}
