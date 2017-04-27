package com.taogu.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

  public static void main(String[] args) throws Exception {
    // 重新启动
    System.setProperty("spring.devtools.restart.enabled", "false");
    // 热加载
    System.setProperty("spring.devtools.livereload.enabled ", "false");
//    PluginManager pm = new PluginManager();
//    pm.setPeriod(1000l);
//    pm.setPluginPath("./plugins");
//    pm.startPluginMonitor();
    // 启动tomcat
    SpringApplication.run(App.class, args);
  }
}