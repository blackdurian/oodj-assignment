package com.oodj.controller;

import com.oodj.model.Customer;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class CustomerSearchController {

    public CustomerSearchController(List<Customer> customers) {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Search Result");
        Menu.getInstance().addItem(mappingMenuItem(customers));
        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , CustomerController::new));
        Menu.getInstance().display();
    }

    private List<MenuItem> mappingMenuItem(List<Customer> customers) {
        List<MenuItem> results = new ArrayList<>();
        for (Customer customer : customers) {
            results.add(new MenuItem(
                    customer.getName()
                    , new String[]{customer.getName()}
                    , () -> new CustomerDetailController(customer)));
        }
        return results;
    }
}