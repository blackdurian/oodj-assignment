package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.CustomerDao;
import com.oodj.model.Customer;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private Scanner sc = new Scanner(System.in);
    private CustomerDao customerDao = new CustomerDao();

    public CustomerController() {
        Menu.getInstance().clear();
        Menu.getInstance().addItem(new MenuItem("Search"
                , new String[]{"s", "search"}
                , new MenuEvent() {
            @Override
            public void execute() {
                System.out.println("Search :");
                String value = sc.nextLine();
                if (!value.trim().isEmpty()){
                    List<Customer> customers = customerDao.search(value);
                    if (customers.size()>0){
                        new CustomerSearchController(customers);
                    }else {
                        System.out.printf("Your Search - %s - did not match any customers%n", value);
                        Menu.getInstance().display();
                    }
                }else {
                    Menu.getInstance().display();
                }
            }
        }));

        Menu.getInstance().addItem(new MenuItem("All Products"
                , new String[]{"all", "all product"}
                , new MenuEvent() {
            @Override
            public void execute() {
                List<Customer> customers = customerDao.findAll();
                if (customers.size()>0){
                    new CustomerSearchController(customers);
                }else {
                    System.out.println("No available customer.");
                    Menu.getInstance().display();
                }
            }
        }));

            Menu.getInstance().addItem(new MenuItem("Add Customer"
                    , new String[]{"add", "add product"}
                    , new MenuEvent() {
                @Override
                public void execute() {
               //TODO : add customer

                }
            }));



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
