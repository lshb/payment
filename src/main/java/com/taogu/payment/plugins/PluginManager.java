package com.taogu.payment.plugins;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.taogu.payment.config.Config;

public class PluginManager {

  private ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

  @Autowired
  private Config config;

  // 开始扫描支付插件
  public void startPluginMonitor() {
    es.scheduleAtFixedRate(new PluginMonitor(config.getPluginPath(), config.getPluginClass()), 0, config.getPluginPeriod(), TimeUnit.SECONDS);
  }

}
