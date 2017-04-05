package com.taogu.payment.util;

import java.util.UUID;

public class StringUtil {

  public static String getUUIDString() {
    try {
      return UUID.randomUUID().toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // 生成22位支付订单号,时间13位+用户6位+类型3位
  public static String generateTradeNo(long userId, int payId) throws Exception {
    StringBuffer sb = new StringBuffer();
    long time = System.currentTimeMillis();
    sb.append(getPreZeroStrFromInt(time, 13));
    sb.append(getPreZeroStrFromInt(userId, 6));
    sb.append(getPreZeroStrFromInt(payId, 3));
    return sb.toString();
  }

  /**
   * 获得前面补0的字符串
   * @param n  数字
   * @param num 总位数
   * @return
   * @throws Exception 
   */
  public static String getPreZeroStrFromInt(long n, int num) throws Exception {
    int length = String.valueOf(n).length();
    if (length > num) {
      throw new Exception("转换数字长度大于总位数！");
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < num - length; i++) {
      sb.append('0');
    }
    sb.append(n);
    return sb.toString();
  }
}
