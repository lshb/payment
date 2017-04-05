package com.taogu.payment.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayAccountDao {

  @Select("SELECT config FROM pay_account WHERE user_id = #{user_id} and type = #{type}")
  String find(@Param("user_id") long user_id,@Param("type") String type);

}
