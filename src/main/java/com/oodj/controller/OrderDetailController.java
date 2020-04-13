package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.AdminDao;
import com.oodj.data.dao.CustomerDao;
import com.oodj.data.dao.OrderDao;
import com.oodj.model.Admin;
import com.oodj.model.CartItem;
import com.oodj.model.Customer;
import com.oodj.model.Order;
import com.oodj.model.Status;
import com.oodj.model.User;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class OrderDetailController {

    private Order order;
    private OrderDao orderDao = new OrderDao();
    private Scanner sc = new Scanner(System.in);

    public OrderDetailController(Order order) {
        this.order = order;
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Order Details");
        Menu.getInstance().setTopMessage(generateDetailMessage());
        Menu.getInstance().addItem(new MenuItem("Add Remark"
                , new String[]{"add remark", "remark"}
                , new MenuEvent() {
            @Override
            public void execute() {
                addRemark();
            }
        }));
        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Mark as DELIVERED"
                    , new String[]{"delivered", "mark as delivered"}
                    , this::setDelivered));

            Menu.getInstance().addItem(new MenuItem("Mark as DROPPED"
                    , new String[]{"drop", "mark as drop"}
                    , this::setDropped));
        }

        if (Application.user instanceof Customer) {
            Menu.getInstance().addItem(new MenuItem("Cancel Order"
                    , new String[]{"cancel", "cancel order"}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    setCancelled();
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"back"}
                , OrderController::new));
        Menu.getInstance().display();
    }

    private void addRemark() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        String currentRemark = order.getRemark();

        System.out.println(currentRemark + "\r\n Add Remark: ");
        String remark = sc.nextLine();
        if (!remark.trim().isEmpty()) {
            remark = currentRemark + "\r\n" + strDate + "\t" + remark;
            order.setRemark(remark);
            orderDao.update(order);
        }
        new OrderDetailController(order);
    }

    private String generateDetailMessage() {
        AdminDao adminDao = new AdminDao();
        User user = adminDao.findOne(order.getUserId());

        CustomerDao customerDao = new CustomerDao();
        if (user == null) {
            user = customerDao.findOne(order.getUserId());
        }

        // Generate String of order detail
        StringBuilder sb = new StringBuilder();
        //Display order id and status
        sb.append("Id:\t").append(order.getId())
                .append("\r\n\tStatus: \t").append(order.getStatus());
        // Display buyer name and ID
        if (user != null) {
            sb.append("\r\nOrder By:\t").append(user.getName())
                    .append("\t\t#").append(user.getId());
        }
        // Column Header
        sb.append("\r\nProduct Id\tName\tQty\tSubtotal\r\n");
        // Put all Cart Items in table view
        for (CartItem cartItem : order.getCartItems()) {
            String row = String.format("%s\t%s\t%d\t$ %.2f%n"
                    , cartItem.getProductID()
                    , cartItem.getProductName()
                    , cartItem.getQuantity()
                    , cartItem.getSubTotal());
            sb.append(row);
        }
        sb.append("----------------------\r\n")
                .append("\t\t\tTotal:\t").append(order.getTotalPrice())
                .append("\r\nRemark:\t").append(order.getRemark())
                .append("\r\n");
        return sb.toString();
    }

    private void setDelivered() {
        order.setStatus(Status.DELIVERED);
        orderDao.update(order);
        new OrderDetailController(order);
    }

    private void setDropped() {
        order.setStatus(Status.DROPPED);
        orderDao.update(order);
        new OrderDetailController(order);
    }

    private void setCancelled() {
        order.setStatus(Status.CANCELLED);
        orderDao.update(order);
        new OrderDetailController(order);
    }
}
