package com.oodj.controller;

import com.oodj.model.CartItem;
import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ShoppingCartController {
    private static final int ORDER_CAPACITY = 50;       // current cart CAPACITY
    private static Set<CartItem> cartItems = new HashSet<>();
    private static String orderId;
    private static double totalPrice;  // total price of cartItems in the cart


    public ShoppingCartController() {
        Menu.getInstance().clear();
        int lastItem = 0;
        for (CartItem cartItem : cartItems) {
            lastItem++;
            String menu = String.format("%s\t\t\t%d\t\t\t%.02f"
                    , cartItem.getProductName()
                    , cartItem.getQuantity()
                    , cartItem.getSubTotal());

            if (lastItem == cartItems.size()){
                     menu = String.format("%s\t\t\t%d\t\t\t%.02f%n"
                                     + "-----------------------------"
                                     + "%n\t\t\t%s\t\t\t%.02f%n"
                                     + "============================="
                             , cartItem.getProductName()
                             , cartItem.getQuantity()
                             , cartItem.getSubTotal()
                             , "Total price"
                             , 1231.12);
            }

            Menu.getInstance().addItem(new MenuItem(menu
                    , new String[]{cartItem.getProductName()}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    System.out.println("Current Edit Quantity");
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Continue purchase"
                , new String[]{"purchase","continue purchase","p"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new ProductController();
            }
        }));

        Menu.getInstance().display();
    }

    public void addItem(Product product){
        if (orderId.isEmpty()){
            orderId = UUID.randomUUID().toString();
        }

        if (cartItems.isEmpty()){

        }
    }

    public void edit(){

    }



}
