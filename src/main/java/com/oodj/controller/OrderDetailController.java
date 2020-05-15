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
import com.oodj.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class OrderDetailController {

    private Order order;
    private OrderDao orderDao = new OrderDao();
    private Scanner sc = new Scanner(System.in);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public OrderDetailController(Order order) {
        this.order = order;
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Order Details");
        Menu.getInstance().setTopMessage(generateDetailMessage());

        Menu.getInstance().addItem(new MenuItem("Add Remark"
                , new String[]{"add remark", "remark"}
                , this::addRemark));

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
                    , this::setCancelled));
        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"back"}
                , OrderController::new));
        Menu.getInstance().display();
    }

    private void addRemark() {
        Date date = Calendar.getInstance().getTime();
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
        //Find customer with id, if not found in admin repo
        if (user == null) {
            user = customerDao.findOne(order.getUserId());
        }
        // Generate String of order detail
        StringBuilder sb = new StringBuilder();
        //Display order date, id and status
        sb.append("Order Date:\t\t")
                .append(dateFormat.format(order.getOrderDate()))
                .append("\r\nOrder ID:\t\t")
                .append(order.getId())
                .append("\r\nStatus:\t\t\t")
                .append(order.getStatus());
        // Display buyer name and ID
        if (user != null) {
            sb.append("\r\nOrder By:\t\t")
                    .append(user.getName())
                    .append("\r\n\t\t\t\t#").append(user.getId());
        } else {
            sb.append("\r\nOrder By:\t\t")
                    .append("N/A")
                    .append("\t\t").append(order.getUserId());
        }
        // Column Header
        sb.append("\r\n\n")
                .append(StringUtils.rightPad("Name", 30, " "))
                .append(StringUtils.leftPad("Qty", 5, " "))
                .append(StringUtils.leftPad("Subtotal($)", 13, " "))
                .append("\r\n");
        //Row values
        for (CartItem cartItem : order.getCartItems()) {
            String row = StringUtils.rightPad(cartItem.getProductName(), 30, " ")
                    + StringUtils.leftPad(Integer.toString(cartItem.getQuantity()), 5, " ")
                    + StringUtils.leftPad(String.format("%.2f", cartItem.getSubTotal()), 11, " ")
                    + "\r\n";
            sb.append(row);
        }
        String total = String.format("Total:    %.2f", order.getTotalPrice());
        sb.append(StringUtils.leftPad("", 47, "-"))
                .append("\r\n")
                .append(StringUtils.leftPad(total, 46, " "))
                .append("\r\nRemark:").append(order.getRemark())
                .append("\r\n\r\n");
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
