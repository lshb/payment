package com.taogu.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

  // 插件的文件夹目录
  @Value("${plugin.path}")
  private String pluginPath;
  // 插件类
  @Value("${plugin.class}")
  private String pluginClass;
  // 插件监控间隔
  @Value("${plugin.period}")
  private long pluginPeriod;

  public String getPluginPath() {
    return pluginPath;
  }

  public void setPluginPath(String pluginPath) {
    this.pluginPath = pluginPath;
  }

  public String getPluginClass() {
    return pluginClass;
  }

  public void setPluginClass(String pluginClass) {
    this.pluginClass = pluginClass;
  }

  public long getPluginPeriod() {
    return pluginPeriod;
  }

  public void setPluginPeriod(long pluginPeriod) {
    this.pluginPeriod = pluginPeriod;
  }

}
