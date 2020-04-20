package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.CustomerDao;
import com.oodj.model.Admin;
import com.oodj.model.Customer;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.Scanner;

public class CustomerDetailController {

    private Customer customer;
    private CustomerDao customerDao = new CustomerDao();
    private Scanner sc = new Scanner(System.in);
    private String successMessage = " Success!";


    public CustomerDetailController(Customer customer) {
        this.customer = customer;
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Customer Detail");
        Menu.getInstance().setTopMessage(getCustomerDetail());
        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Edit Name"
                    , new String[]{"edit name", "update name"}
                    , this::editName));

            Menu.getInstance().addItem(new MenuItem("Edit Password"
                    , new String[]{"edit password", "update password"}
                    , this::editPassword)); //method reference

            Menu.getInstance().addItem(new MenuItem("Edit IC"
                    , new String[]{"edit ic", "update ic"}
                    , () -> editIC())); //lambda

            Menu.getInstance().addItem(new MenuItem("Edit Address"
                    , new String[]{"edit address", "update address"}
                    , this::editAddress)); //method reference

            Menu.getInstance().addItem(new MenuItem("Edit Contact Number"
                    , new String[]{"edit contactNum", "update contactNum"}
                    , () -> editContactNum())); //lambda

            Menu.getInstance().addItem(new MenuItem("Delete Customer"
                    , new String[]{"delete", "delete customer"}
                    , new MenuEvent() { //most common
                @Override
                public void execute() {
                    customerDao.delete(customer.getId());
                    new CustomerController();
                }
            }));
        }
        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"back"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new CustomerController();
            }
        }));
        Menu.getInstance().display();
    }

    private String getCustomerDetail() {
        return String.format("[%s]%n Id:\t\t%s%n Username:\t%s%n Password:\t%s%n" +
                        " IC:\t\t%s%n Address:\t%s%n ContactNum:%s%n%n"
                , customer.getName()
                , customer.getId()
                , customer.getUsername()
                , customer.getPassword()
                , customer.getIcNum()
                , customer.getAddress()
                , customer.getContactNum());
    }

    private void editName() { //encap
        System.out.println("New Name:");
        String name = sc.nextLine();
        if (!customerDao.nameExists(name)) {
            customer.setName(name);
            customerDao.update(customer);
            System.out.println(successMessage);
        } else {
            System.out.println("\"" + name + "\" ,Name has been taken.");
        }
        new CustomerDetailController(customer);
    }

    private void editPassword() { //encap
        System.out.println("New Password:");
        String password = sc.nextLine();
        customer.setPassword(password);
        customerDao.update(customer);
        System.out.println(successMessage);
        new CustomerDetailController(customer);
    }

    private void editIC() { //encap
        System.out.println("New IC Number:");
        String ic = sc.nextLine();
        if (!customerDao.icNumExists(ic)) {
            customer.setIcNum(ic);
            customerDao.update(customer);
            System.out.println(successMessage);
        } else {
            System.out.println("\"" + ic + "\" ,This IC has existed");
        }
        new CustomerDetailController(customer);
    }

    private void editAddress() { //encap
        System.out.println("New Address:");
        String address = sc.nextLine();
        customer.setAddress(address);
        customerDao.update(customer);
        System.out.println(successMessage);
        new CustomerDetailController(customer);
    }

    private void editContactNum() { //encap
        System.out.println("New Contact Number:");
        String contact = sc.nextLine();
        if (!customerDao.contactNumExists(contact)) {
            customer.setContactNum(contact);
            customerDao.update(customer);
            System.out.println(successMessage);
        } else {
            System.out.println("\"" + contact + "\" ,This Contact Number has existed");
        }
        new CustomerDetailController(customer);
    }


}
