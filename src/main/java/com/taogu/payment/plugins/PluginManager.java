package com.taogu.payment.plugins;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PluginManager {

  private ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
  // 监控时长
  private long period;
  // 插件地址
  private String pluginPath;

  // 开始扫描支付插件
  public void startPluginMonitor() {
    es.scheduleAtFixedRate(new PluginMonitor(pluginPath), 0, period, TimeUnit.SECONDS);
  }

  public long getPeriod() {
    return period;
  }

  public void setPeriod(long period) {
    this.period = period;
  }

  public String getPluginPath() {
    return pluginPath;
  }

  public void setPluginPath(String pluginPath) {
    this.pluginPath = pluginPath;
  }
}
