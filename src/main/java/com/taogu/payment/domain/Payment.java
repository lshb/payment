package com.taogu.payment.domain;

import java.util.Date;

public class Payment {

  private long id;
  private String order_id;
  private int cost;
  private int status;
  private Date create_time;
  private Date update_time;
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getOrder_id() {
    return order_id;
  }
  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }
  public int getCost() {
    return cost;
  }
  public void setCost(int cost) {
    this.cost = cost;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public Date getCreate_time() {
    return create_time;
  }
  public void setCreate_time(Date create_time) {
    this.create_time = create_time;
  }
  public Date getUpdate_time() {
    return update_time;
  }
  public void setUpdate_time(Date update_time) {
    this.update_time = update_time;
  }
}
