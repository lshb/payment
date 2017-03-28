package com.taogu.payment;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;

import com.taogu.payment.bean.PayParams;
import com.taogu.payment.exception.PayExeption;
import com.taogu.payment.util.ClassUtil;

import sun.misc.ClassLoaderUtil;

public class PayManager {

  private final static Logger log = Logger.getLogger("PayChannel");
  private static HashMap<String, Class<Pay>> payMap = new HashMap<String, Class<Pay>>();
  private static ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
  // 10s中扫描一次插件目录
  private long period = 10;

  private HashSet<String> plugins = new HashSet<>();
  private String pluginPath = "./plugins";
  private String pluginConfigFileName = "plugin.properties";

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

  /**
   * 退费
   * @param orderId 退费的订单id
   * @return
   */
  public static boolean refund(String orderId) {

    return false;
  }

  public static Class<Pay> addPayType(Class<Pay> pay) {
    log.info("add one payType ,the type is" + pay.getName());
    return payMap.put(pay.getName().toLowerCase(), pay);
  }

  public static Set<String> getPayTypes() {
    return payMap.keySet();
  }

  public static Class<Pay> getPay(String type) {
    return payMap.get(type);
  }
}
