package com.taogu.payment.plugins;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.taogu.payment.system.Pay;
import com.taogu.payment.system.PayClassManager;
import com.taogu.payment.util.ClassUtil;

/**
 * 插件监控类
 * @author speedy
 *
 */
public class PluginMonitor implements Runnable {

  private final static Logger log = Logger.getLogger(PluginMonitor.class);
  // 保存已经加载的plugin路径
  private String pluginPath;
  private String pluginClass;
  private long lastLoadTime;

  private final static String PROPERTY_SEPARATOR = ",";

  public PluginMonitor(String pluginPath, String pluginClass) {
    this.pluginPath = pluginPath;
    this.pluginClass = pluginClass;
  }

  // scan the plugins fold and find the pay
  public void pluginScan() {
    log.info("开始扫描加载plugin文件夹中的插件！");
    File dir = new File(pluginPath);
    ClassUtil.loopFiles(dir, new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (lastLoadTime < file.lastModified() && file.getName().endsWith(".jar")) {
          return true;
        }
        return false;
      }
    });
    // 修改插件上次加载时间
    lastLoadTime = System.nanoTime();
    if (!StringUtils.isEmpty(pluginClass)) {
      String[] split = pluginClass.split(PROPERTY_SEPARATOR);
      for (String string : split) {
        Class<Pay> pay = null;
        try {
          log.info("加载插件：" + string);
          pay = (Class<Pay>) ClassUtil.loadClass(string);
          PayClassManager.addPayType(pay);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    log.info("加载plugin插件结束！");
  }

  @Override
  public void run() {
    pluginScan();
  }

}
