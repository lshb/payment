package com.taogu.payment.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassUtil {

  // 初始化类的实例
  public static <T> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
    return clazz.newInstance();
  }

  // 加载name路径下的类
  public static Class loadClass(String className, String jarPath) throws MalformedURLException, ClassNotFoundException {
    URL url = new URL(jarPath);
    URLClassLoader ucl = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
    return ucl.loadClass(className);
  }
  
  public static void test(){
    
  }
}
