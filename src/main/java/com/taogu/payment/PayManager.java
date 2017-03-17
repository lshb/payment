package com.taogu.payment;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.taogu.payment.bean.BusinessData;
import com.taogu.payment.exception.BusinessDataException;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.util.ClassUtil;

import sun.misc.ClassLoaderUtil;

public class PayManager {

  private final static Logger log = Logger.getLogger("PayChannel");
  private static HashMap<String, Class<Pay>> payMap = new HashMap<String, Class<Pay>>();
  private static HashMap<String, Class<Business>> businessMap = new HashMap<String, Class<Business>>();
  private static ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
  private long period = 10;

  /**
   * 下单接口
   * @param data 订单业务数据
   * @return 订单id
   * @throws IllegalAccessException 
   * @throws InstantiationException 
   * @throws BusinessDataException 
   */
  public static String makeOrder(BusinessData data) throws PayExeption, InstantiationException, IllegalAccessException {
    Class<Business> businessClass = businessMap.get(data.getBusinessType());
    if (businessClass == null) {
      throw new PayExeption("not support this businessType!");
    }
    if (ClassUtil.getInstance(businessClass).saveBusinessData(data)) {
      Class<Pay> payClass = payMap.get(data.getPayType());
      if (payClass == null) {
        throw new PayExeption("not support this payType!");
      }
      String orderId = ClassUtil.getInstance(payClass).unifiedOrder(data);
      log.info("the order make success: " + orderId);
      return orderId;
    }
    log.info("the order make failed!");
    return null;
  }

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

  public static Class<Pay> addChannel(Class<Pay> pay) {
    log.info("add one channel ,the name is" + pay.getName());
    return payMap.put(pay.getName().toLowerCase(), pay);
  }

  public static Set<String> getChannels() {
    return payMap.keySet();
  }

  public static Class<Pay> getPay(String name) {
    return payMap.get(name);
  }
}
