package com.taogu.payment.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public final class ClassUtil {

  private static Method addURL = initAddMethod();
  /** 
   * 初始化addUrl 方法.
   * @return 可访问addUrl方法的Method对象
   */
  private static Method initAddMethod() {
    try {
      Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
      add.setAccessible(true);
      return add;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 通过filepath加载文件到classpath。
   * @param filePath 文件路径
   * @return URL
   * @throws Exception 异常
   */
  private static void addURL(File file) {
    try {
      addURL.invoke(getClassLoad(), new Object[] { file.toURI().toURL() });
    } catch (Exception e) {
    }
  }

  private static ClassLoader getClassLoad() {
    return Thread.currentThread().getContextClassLoader();
  }

  /** 
   * 循环遍历目录，找出所有的资源路径。
   * @param file 当前遍历文件
   */
  public static void loopDirs(File file) {
    // 资源文件只加载路径
    if (file.isDirectory()) {
      addURL(file);
      File[] tmps = file.listFiles();
      for (File tmp : tmps) {
        loopDirs(tmp);
      }
    }
  }

  /** 
   * 循环遍历目录，找出所有符合filter的文件
   * @param file 当前遍历文件
   */
  public static void loopFiles(File file, FileFilter filter) {
    if (file.isDirectory()) {
      File[] tmps = file.listFiles(filter);
      for (File tmp : tmps) {
        loopFiles(tmp, filter);
      }
    } else {
      if (filter.accept(file)) {
        addURL(file);
      }
    }
  }

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
   * 加载class对象
   * @param className  class的全路径
   * @return
   * @throws ClassNotFoundException
   */
  public static Class<?> loadClass(String className) throws ClassNotFoundException {
    return getClassLoad().loadClass(className);
  }

}
