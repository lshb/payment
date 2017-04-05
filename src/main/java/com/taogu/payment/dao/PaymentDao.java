package com.taogu.payment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.taogu.payment.domain.Payment;

@Mapper
public interface PaymentDao {

  @Select("SELECT * FROM payment WHERE user_id = #{userId}")
  List<Payment> findByUser(@Param("userId") int userId);

  @Insert("INSERT INTO payment(order_id, cost,status,user_id,type) VALUES(#{orderId}, #{cost},#{status}),#{userId},#{type}")
  int insert(@Param("orderId") String orderId, @Param("cost") int cost, @Param("status") int status, @Param("userId") int userId,
      @Param("type") int type);

}
