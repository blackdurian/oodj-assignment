package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.OrderDao;
import com.oodj.model.Admin;
import com.oodj.model.Customer;
import com.oodj.model.Order;
import com.oodj.model.User;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    private Scanner sc = new Scanner(System.in);
    private OrderDao orderDao = new OrderDao();
    private User user = Application.user;

    public OrderController() {

        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Manage Orders");
        if (user.getClass() == Admin.class) {
            Menu.getInstance().addItem(new MenuItem("Search"
                    , new String[]{"s", "search"}
                    , () -> {
                System.out.println("Search :");
                String searchText = sc.nextLine();
                if (!searchText.trim().isEmpty()) {
                    List<Order> orders = orderDao.search(searchText);
                    if (orders.size() > 0) {
                        new OrderSearchController(orders);
                    } else {
                        System.out.printf("Your Search - %s - did not match any orders%n",
                                searchText);
                        Menu.getInstance().display();
                    }
                } else {
                    Menu.getInstance().display();
                }
            }));

            Menu.getInstance().addItem(new MenuItem("View All Orders"
                    , new String[]{"view all", "view all orders"}
                    , () -> {
                List<Order> orders = orderDao.findAll();
                if (orders.size() > 0) {
                    new OrderSearchController(orders);
                } else {
                    System.out.println("No order.");
                    Menu.getInstance().display();
                }
            }));
        }

        if (user.getClass() == Customer.class) {
            Menu.getInstance().addItem(new MenuItem("Search"
                    , new String[]{"s", "search"}
                    , () -> {
                System.out.println("Search :");
                String searchText = sc.nextLine();
                if (!searchText.trim().isEmpty()) {
                    List<Order> orders = orderDao.search(searchText, user.getId());
                    if (orders.size() > 0) {
                        new OrderSearchController(orders);
                    } else {
                        System.out.printf("Your Search - %s - did not match any orders%n",
                                searchText);
                        Menu.getInstance().display();
                    }
                } else {
                    Menu.getInstance().display();
                }
            }));

            Menu.getInstance().addItem(new MenuItem("View All Orders"
                    , new String[]{"orders", "view all orders", "view all"}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    List<Order> orders = orderDao.findByUser(user.getId());
                    if (orders.size() > 0) {
                        new OrderSearchController(orders);
                    } else {
                        System.out.println("No order.");
                        Menu.getInstance().display();
                    }
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new HomeController();
            }
        }));
        Menu.getInstance().display();
    }
}
