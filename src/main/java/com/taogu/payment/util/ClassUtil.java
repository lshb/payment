package com.taogu.payment.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassUtil {

  /**
   * 初始化class的实例
   * @param clazz 类对象
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static <T> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
    return clazz.newInstance();
  }

  /**
   * 加载指定jar中的class对象
   * @param className class的全路径
   * @param jarPath 指定jar的路径
   * @return
   * @throws MalformedURLException
   * @throws ClassNotFoundException
   */
  public static Class<?> loadClass(String className, String jarPath) throws MalformedURLException, ClassNotFoundException {
    URL url = new URL(jarPath);
    URLClassLoader ucl = new URLClassLoader(new URL[] { url }, Thread.currentThread().getContextClassLoader());
    return ucl.loadClass(className);
  }

  /**
   * 加载class对象
   * @param className  class的全路径
   * @return
   * @throws ClassNotFoundException
   */
  public static Class<?> loadClass(String className) throws ClassNotFoundException {
    return Thread.currentThread().getContextClassLoader().loadClass(className);
  }
}
