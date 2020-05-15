package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.OrderDao;
import com.oodj.model.CartItem;
import com.oodj.model.Order;
import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class ShoppingCartController {

    private static final int MAX_PRODUCT_QTY = 50;       // current cart CAPACITY
    private static List<CartItem> cartItems = new ArrayList<>();
    private static String orderId = "";
    private final Scanner sc = new Scanner(System.in);

    public ShoppingCartController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Shopping Cart");
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            String menu = String.format("%s\t%d\t\t\t%.02f"
                    , StringUtils.rightPad(item.getProductName(), 20, " ")
                    , item.getQuantity()
                    , item.getSubTotal());

            if (i == cartItems.size() - 1) {
                menu = String.format("%s\t%d\t\t\t%.02f%n"
                                     + "------------------------------"
                                     + "%n\t\t\t\t%s\t\t\t%.02f%n"
                                     + "=============================="
                        , StringUtils.rightPad(item.getProductName(), 20, " ")
                             , item.getQuantity()
                             , item.getSubTotal()
                             , "Total price"
                             , getTotal());
            }

            Menu.getInstance().addItem(new MenuItem(menu
                    , new String[]{item.getProductName()}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    System.out.println("Current Edit Quantity");

                    try {
                        int qty = sc.nextInt();
                        if (qty <= MAX_PRODUCT_QTY) {
                            editItem(item.getProductID(), qty);
                        } else {
                            System.out.println("Quantity cannot more than " + MAX_PRODUCT_QTY);
                        }
                    } catch (InputMismatchException ignored) {
                        System.out.println("Wrong input, number only.");
                    }
                    new ShoppingCartController();
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Continue purchase"
                , new String[]{"purchase","continue purchase","p"}
                , ProductController::new));

        Menu.getInstance().addItem(new MenuItem("Proceed Order"
                , new String[]{"proceed order", "order"}
                , this::order));

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"back", "b"}
                , HomeController::new));
        Menu.getInstance().display();
    }

    private void order() {
        System.out.println("Please confirm your order. (Y/N)");
        String confirm = sc.nextLine();
        if (confirm.trim().equalsIgnoreCase("y")) {
            Order order = new Order(orderId
                    , new Date()
                    , Application.user.getId()
                    , cartItems, getTotal());
            new OrderDao().add(order);
            new OrderDetailController(order);
        } else {
            Menu.getInstance().display();
        }
    }

    public static void addItem(Product product) {
        // initial Order ID
        if (orderId.isEmpty()){
            orderId = UUID.randomUUID().toString();
        }
        // map product to cartItem
        CartItem newItem = new CartItem(orderId, product.getId(), product.getName(), 1,
                product.getPrice(), product.getPrice());
        boolean itemExist = false;
        // find same product
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.equals(newItem)) {
                itemExist = true;
                int newQty = newItem.getQuantity() + item.getQuantity();
                double newSubtotal = newQty * newItem.getUnitPrice();
                item.setQuantity(newQty);
                item.setSubTotal(newSubtotal);
                //update list
                cartItems.set(i, item);
            }
        }

        if (!itemExist) {
            cartItems.add(newItem);
        }
    }

    private void editItem(String productId, int quantity) {
        if (quantity == 0) {
            cartItems.removeIf(item -> item.getProductID().equals(productId));
        } else {
            for (CartItem item : cartItems) {
                if (item.getProductID().equals(productId)) {
                    double subTotal = item.getUnitPrice() * quantity;
                    item.setQuantity(quantity);
                    item.setSubTotal(subTotal);
                }
            }
        }
    }

    private Double getTotal() {
        return cartItems.stream().mapToDouble(CartItem::getSubTotal).sum();
    }

}
