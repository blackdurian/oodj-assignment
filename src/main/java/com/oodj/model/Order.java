package com.oodj.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order {

    private String id;
    private Date orderDate;
    private String userId;
    private List<CartItem> cartItems;
    private String remark;

    private Status status = Status.PROCESSING;
    private double totalPrice;

    public Order() {
    }

    public Order(String id, Date orderDate, String userId,
            List<CartItem> cartItems, double totalPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.userId = userId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public Order(String id, Date orderDate, String userId,
            List<CartItem> cartItems, String remark, Status status, double totalPrice) {
        this.id = id;
        this.orderDate = orderDate;
        this.userId = userId;
        this.cartItems = cartItems;
        this.remark = remark;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> CartItems) {
        this.cartItems = CartItems;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id +
                " " + orderDate +
                " " + userId +
                " " + cartItems +
                " " + remark +
                " " + status +
                " " + totalPrice;
    }
}
