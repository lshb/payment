package com.taogu.payment.util;

import org.springframework.util.StringUtils;

public class ValidateUtil {

  /**
   * 验证字符串数组是否都不为空
   * @param strings
   * @return
   */
  public static boolean stringsHaveEmpty(String... strings) {
    for (String string : strings) {
      if (StringUtils.isEmpty(string)) {
        return true;
      }
    }
    return false;
  }
}
