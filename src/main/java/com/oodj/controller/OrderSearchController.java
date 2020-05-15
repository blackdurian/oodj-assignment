package com.oodj.controller;

import com.oodj.model.Order;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderSearchController {

    public OrderSearchController(List<Order> orders) {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Search Result");
        Menu.getInstance().addItem(mappingMenuItem(orders));
        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , OrderController::new));
        Menu.getInstance().display();
    }

    private List<MenuItem> mappingMenuItem(List<Order> orders) {
        List<MenuItem> results = new ArrayList<>();
        for (Order order : orders) {
            Date date = order.getOrderDate();
            results.add(new MenuItem(
                    String.format("%s\t\tDate: %td/%tm/%tY\t\tAmount: %.2f\t\t%s"
                            , order.getId()
                            , date, date, date
                            , order.getTotalPrice()
                            , order.getStatus())
                    , new String[]{order.getId()}
                    , () -> new OrderDetailController(order)));
        }
        return results;
    }
}
