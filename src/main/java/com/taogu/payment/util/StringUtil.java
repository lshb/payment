package com.taogu.payment.util;

import java.util.UUID;

public class StringUtil {

  public String getUUIDString() {
    try {
      return UUID.randomUUID().toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
