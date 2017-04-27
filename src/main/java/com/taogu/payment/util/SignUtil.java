package com.taogu.payment.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class SignUtil {

  /**
   * 生成微信签名
   * @param map
   * @return
   */
  public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
    String buff = "";
    try {
      List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
      // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
      Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

        @Override
        public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
          return (o1.getKey()).toString().compareTo(o2.getKey());
        }
      });
      // 构造URL 键值对的格式
      StringBuilder buf = new StringBuilder();
      for (Map.Entry<String, String> item : infoIds) {
        if (!StringUtils.isEmpty(item.getKey())&&!StringUtils.isEmpty(item.getValue())) {
          String key = item.getKey();
          String val = item.getValue();
          if (urlEncode) {
            val = URLEncoder.encode(val, "utf-8");
          }
          if (keyToLower) {
            buf.append(key.toLowerCase() + "=" + val);
          } else {
            buf.append(key + "=" + val);
          }
          buf.append("&");
        }
      }
      buff = buf.toString();
      if (buff.isEmpty() == false) {
        buff = buff.substring(0, buff.length() - 1);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return buff;
  }

}
