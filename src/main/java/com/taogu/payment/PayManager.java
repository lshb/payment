package com.taogu.payment;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.taogu.payment.bean.PayParams;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.util.ClassUtil;

public class PayManager {

  private final static Logger log = Logger.getLogger("PayChannel");
  private static HashMap<String, Class<Pay>> payMap = new HashMap<String, Class<Pay>>();
  private static ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
  // 10s中扫描一次插件目录
  private long period = 10;

  /**
   * 下单接口
   * @param params 订单业务数据
   * @return 订单
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws PayExeption 
   */
  public static Map<String, Object> makeOrder(PayParams params) throws PayExeption, InstantiationException, IllegalAccessException {
    Class<Pay> payClass = payMap.get(params.getPayType());
    if (payClass == null) {
      throw new PayExeption("not support this payType!");
    }
    Map<String, Object> orderMap = ClassUtil.getInstance(payClass).unifiedOrder(params);
    log.info("the order make success: " + orderMap);
    return orderMap;
  }

  public static Class<Pay> addPayType(Class<Pay> pay) {
    log.info("add one payType ,the type is" + pay.getName());
    return payMap.put(pay.getName().toLowerCase(), pay);
  }

  // 开始扫描支付插件
  public void startPluginScan() {
    es.scheduleAtFixedRate(new Runnable() {
      public void run() {
        // scan the plugins fold and find the pay or bussiness
        String className = "";
        String jarPath = "";
        try {
          ClassUtil.loadClass(className, jarPath);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }, period, period, TimeUnit.SECONDS);
  }

  public static Set<String> getPayTypes() {
    return payMap.keySet();
  }

  public static Class<Pay> getPay(String type) {
    return payMap.get(type);
  }
}
