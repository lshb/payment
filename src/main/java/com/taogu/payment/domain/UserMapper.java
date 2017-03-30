package com.taogu.payment.domain;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

  @Select("SELECT * FROM user WHERE nick_name = #{nickName}")
  User findByName(@Param("nickName") String name);

  @Insert("INSERT INTO user(nick_name, password) VALUES(#{nickName}, #{password})")
  int insert(@Param("nickName") String nickName, @Param("password") String password);

  @Update("update user set password=#{password} where id = #{id}")
  boolean update(@Param("id") long id, @Param("password") String password);
}