package com.taogu.payment.plugins;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.taogu.payment.Pay;
import com.taogu.payment.PayManager;
import com.taogu.payment.util.ClassUtil;

/**
 * 插件监控类
 * @author speedy
 *
 */
public class PluginMonitor implements Runnable {

  // 保存已经加载的plugin路径
  private Set<String> set = new HashSet<>();
  // 插件地址
  private String pluginPath;
  private static String configName = "plugin.properties";
  private static String pluginClassKey = "plugin.class";

  public PluginMonitor(String pluginPath) {
    this.pluginPath = pluginPath;
  }

  // scan the plugins fold and find the pay
  public void pluginScan() {
    File dir = new File(pluginPath);
    ClassUtil.loopFiles(dir, new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (set.contains(file.getAbsolutePath())) {
          return false;
        } else {
          boolean flag = file.getName().endsWith(".jar");
          if (flag) {
            set.add(file.getAbsolutePath());
          }
          return flag;
        }
      }
    });
    Properties p = new Properties();
    try {
      p.load(new FileReader(pluginPath + File.separator + configName));
    } catch (IOException e) {
      e.printStackTrace();
    }
    String pluginClass = p.getProperty(pluginClassKey);
    String[] split = pluginClass.split(",");
    for (String string : split) {
      Class<Pay> pay = null;
      try {
        pay = (Class<Pay>) ClassUtil.loadClass(string);
        PayManager.addPayType(pay);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void run() {
    pluginScan();
  }

}
