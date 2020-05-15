package com.oodj.controller;

import com.oodj.data.dao.CustomerDao;
import com.oodj.model.Customer;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private final Scanner sc = new Scanner(System.in);
    private final CustomerDao customerDao = new CustomerDao();

    public CustomerController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Manage Customer");
        Menu.getInstance().addItem(new MenuItem("Search"
                , new String[]{"s", "search"}
                , () -> {
            System.out.println("Search :");
            String value = sc.nextLine();
            if (!value.trim().isEmpty()) {
                List<Customer> customers = customerDao.search(value);
                if (customers.size() > 0) {
                    new CustomerSearchController(customers);
                } else {
                    System.out.printf("Your Search - %s - did not match any customers%n", value);
                    Menu.getInstance().display();
                }
            } else {
                Menu.getInstance().display();
            }
        }));

        Menu.getInstance().addItem(new MenuItem("All Customer"
                , new String[]{"all", "all customer"}
                , () -> {
            List<Customer> customers = customerDao.findAll();
            if (customers.size() > 0) {
                new CustomerSearchController(customers);
            } else {
                System.out.println("No available customer.");
                Menu.getInstance().display();
            }
        }));

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , HomeController::new));
        Menu.getInstance().display();

    }
}
