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
    private final CustomerDao customerDao = new CustomerDao();
    private final Scanner sc = new Scanner(System.in);
    private final String successMessage = " Success!";


    public CustomerDetailController(Customer customer) {
        this.customer = customer;
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Profile");
        Menu.getInstance().setTopMessage(getCustomerDetail());

        Menu.getInstance().addItem(new MenuItem("Edit Name"
                , new String[]{"edit name", "update name"}
                , this::editName));

            Menu.getInstance().addItem(new MenuItem("Change Password"
                    , new String[]{"change password", "password"}
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

        if (Application.user instanceof Admin) {
            Menu.getInstance().setHeader("Customer Detail");
            Menu.getInstance().addItem(new MenuItem("Delete Customer"
                    , new String[]{"delete", "delete customer"}
                    , new MenuEvent() { //most common
                @Override
                public void execute() {
                    customerDao.delete(customer.getId());
                    new CustomerController();
                }
            }));
            Menu.getInstance().addItem(new MenuItem("Back"
                    , new String[]{"back"}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    new CustomerController();
                }
            }));
        }

        if (Application.user instanceof Customer) {
            Menu.getInstance().addItem(new MenuItem("Back"
                    , new String[]{"back"}
                    , () -> {
                        Application.user = this.customer;
                        new HomeController();
                    }));
        }

        Menu.getInstance().display();
    }

    private String getCustomerDetail() {
        return String.format("Name:\t\t%s%n Id:\t\t\t%s%n Username:\t\t%s%n" +
                        " IC number:\t%s%n Address:\t\t%s%n Contact Number:\t%s%n%n"
                , customer.getName()
                , customer.getId()
                , customer.getUsername()
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
        customer.setPassword(PasswordUtil.getSHA(password));
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
