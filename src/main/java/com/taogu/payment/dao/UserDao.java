package com.taogu.payment.dao;

import org.apache.ibatis.annotations.*;

import com.taogu.payment.domain.User;

@Mapper
public interface UserDao {

  @Select("SELECT * FROM user WHERE nick = #{nick}")
  User findByNick(@Param("nick") String nick);

  @Insert("INSERT INTO user(nick, password,phone) VALUES(#{nick}, #{password},#{phone})")
  Integer insert(@Param("nick") String nick, @Param("password") String password, @Param("phone") String phone);

  @Update("update user set password=#{password} where id = #{id}")
  Boolean update(@Param("id") long id, @Param("password") String password);

  @Select("SELECT id FROM user WHERE nick = #{nick}")
  Long exit(@Param("nick") String nick);
}