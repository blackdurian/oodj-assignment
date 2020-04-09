package com.oodj.controller;


import com.oodj.Application;
import com.oodj.model.Admin;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;

//@MenuRequest( "home")
public class HomeController {

    public HomeController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Home Menu");
        Menu.getInstance().addItem(new MenuItem("Manage Products"
                , new String[]{"product", "manage products"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new ProductController();
            }
        }));

        Menu.getInstance().addItem(new MenuItem("Manage Orders"
                , new String[]{"order", "manage orders"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new OrderController();
            }
        }));

        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Manage Customers"
                    , new String[]{"customer", "manage customer"}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    new CustomerController();
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Shopping Cart"
                , new String[]{"cart", "shopping cart"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new ShoppingCartController();
            }
        }));

        Menu.getInstance().addItem(new MenuItem("Logout"
                , new String[]{"logout", "log-out"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new LoginController();
                Application.user = null;
            }
        }));

        Menu.getInstance().addItem(new MenuItem("Exit"
                , new String[]{"exit", "close"}
                , new MenuEvent() {
            @Override
            public void execute() {
                System.out.println("Exit");
                Application.exit();
            }
        }));
        Menu.getInstance().display();
    }



}
