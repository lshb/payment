package com.taogu.payment.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

  /**
   * md5加密
   * @param origin
   * @return
   * @throws NoSuchAlgorithmException 
   * @throws UnsupportedEncodingException 
   */
  public static String md5(String origin) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return MD5.MD5Encode(origin);
  }
}
