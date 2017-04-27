package com.taogu.payment.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayAccountDao {

  @Select("SELECT config FROM pay_account WHERE app_id = #{appId} and type = #{type}")
  String find(@Param("appId") String appId, @Param("type") String type);

  @Insert("INSERT INTO pay_account(app_id,type,config) values(#{appId},#{type},#{config}) ON DUPLICATE KEY UPDATE config=VALUES(config)")
  void insertOrUpdate(@Param("appId") String appId, @Param("type") String type, @Param("config") String config);

}
