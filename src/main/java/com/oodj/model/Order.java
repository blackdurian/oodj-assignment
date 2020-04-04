package com.oodj.model;

import java.util.Date;
import java.util.List;

public class Order {

    private String id;
    private Date orderDate;
    private Admin admin;
    private List<CartItem> cartItems;
    private String remark;
    private boolean hasComplete;
    private double totalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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

    public boolean isHasComplete() {
        return hasComplete;
    }

    public void setHasComplete(boolean hasComplete) {
        this.hasComplete = hasComplete;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Order) {
            Order o = (Order) obj;
            if ((o.getId() == null && id == null)
                    || (o.getId().equals(id))) {
                return true;
            }
        }
        return false;
    }
}
