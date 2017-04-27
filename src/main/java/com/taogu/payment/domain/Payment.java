package com.taogu.payment.domain;

public class Payment {

  private long id;
  private String orderId;
  private int cost;
  private int status;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
}
