package com.oodj.controller;


import com.oodj.Application;
import com.oodj.model.Admin;
import com.oodj.model.Customer;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;

public class HomeController {

    public HomeController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Home Menu");

        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Manage Products"
                    , new String[]{"product", "manage products"}
                    , ProductController::new));
            Menu.getInstance().addItem(new MenuItem("Manage Orders"
                    , new String[]{"order", "manage orders"}
                    , OrderController::new));
            Menu.getInstance().addItem(new MenuItem("Manage Customers"
                    , new String[]{"customer", "manage customer"}
                    , CustomerController::new));
        }

        if (Application.user instanceof Customer) {
            Menu.getInstance().addItem(new MenuItem("Products"
                    , new String[]{"product", "products"}
                    , ProductController::new));
            Menu.getInstance().addItem(new MenuItem("Manage Orders"
                    , new String[]{"order", "manage orders"}
                    , OrderController::new));
            Menu.getInstance().addItem(new MenuItem("Profile"
                    , new String[]{"profile", "manage profile"}
                    , () -> new CustomerDetailController((Customer) Application.user)));
        }


        Menu.getInstance().addItem(new MenuItem("Shopping Cart"
                , new String[]{"cart", "shopping cart"}
                , ShoppingCartController::new));

        Menu.getInstance().addItem(new MenuItem("Logout"
                , new String[]{"logout", "log-out"}
                , () -> {
            new LoginController();
            Application.user = null;
        }));

        Menu.getInstance().addItem(new MenuItem("Exit"
                , new String[]{"exit", "close"}
                , () -> {
            System.out.println("Exit");
            Application.exit();
        }));

        Menu.getInstance().display();
    }



}
