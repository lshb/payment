package com.taogu.payment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.taogu.payment.domain.App;

@Mapper
public interface AppDao {

  @Select("SELECT * FROM app WHERE id = #{id}")
  App findById(@Param("id") String id);
  
  @Select("SELECT * FROM app WHERE user_id = #{userId}")
  List<App> findByUser(@Param("userId") long userId);

  @Insert("INSERT INTO app(id, user_id) VALUES(#{id}, #{userId})")
  int insert(@Param("id") String id, @Param("userId") long userId);

}
