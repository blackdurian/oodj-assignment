package com.oodj.model;

import java.util.Objects;

public class CartItem {
   private String orderID;
   private String productID;
   private String productName;
   private int quantity;
   private double unitPrice;
   private double subTotal;

   public CartItem(String orderID, String productID, String productName, int quantity,
           double unitPrice, double subTotal) {
      this.orderID = orderID;
      this.productID = productID;
      this.productName = productName;
      this.quantity = quantity;
      this.unitPrice = unitPrice;
      this.subTotal = subTotal;
   }

   public CartItem() {
   }

   public double getUnitPrice() {
      return unitPrice;
   }

   public void setUnitPrice(double unitPrice) {
      this.unitPrice = unitPrice;
   }

   public String getOrderID() {
      return orderID;
   }

   public void setOrderID(String orderID) {
      this.orderID = orderID;
   }

   public String getProductID() {
      return productID;
   }

   public void setProductID(String productID) {
      this.productID = productID;
   }

   public String getProductName() {
      return productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public double getSubTotal() {
      return subTotal;
   }

   public void setSubTotal(double subTotal) {
      this.subTotal = subTotal;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      CartItem cartItem = (CartItem) o;
      return orderID.equals(cartItem.orderID) &&
              productID.equals(cartItem.productID);
   }

   @Override
   public int hashCode() {
      return Objects.hash(orderID, productID);
   }


}
