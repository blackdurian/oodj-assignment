package com.oodj.controller;

import com.oodj.model.Order;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;

import java.util.Date;
import java.util.List;

class OrderSearchController {

    OrderSearchController(List<Order> orders) {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Search Result");
        for (Order order : orders) {
            Date date = order.getOrderDate();
            Menu.getInstance().addItem(new MenuItem(
                    String.format("%s\t\tDate: %td/%tm/%tY\t\tAmount: %.2f\t\t%s"
                            , order.getId()
                            , date, date, date
                            , order.getTotalPrice()
                            , order.getStatus())
                    , new String[]{order.getId()}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    new OrderDetailController(order);
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , () -> new OrderController()));
        Menu.getInstance().display();
    }
}
