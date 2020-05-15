package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.AdminDao;
import com.oodj.data.dao.CustomerDao;
import com.oodj.model.Admin;
import com.oodj.model.Customer;
import com.oodj.model.User;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;

import java.util.Scanner;
import java.util.UUID;

public class LoginController {
    private static final String ADMIN_REGISTER_CODE = "0000";
    private final Scanner sc = new Scanner(System.in);

    public LoginController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Welcome");
        Menu.getInstance().addItem(new MenuItem("Login"
                , new String[]{"login", "log-in"}
                , this::login));
        Menu.getInstance().addItem(new MenuItem(
                "Register"
                , new String[]{"register", "sign-up"}
                , this::register));
        Menu.getInstance().addItem(new MenuItem("Exit"
                , new String[]{"exit", "close"}
                , this::close));
        Menu.getInstance().display();
    }

    private void close() {
        System.out.println("Exit");
        Application.exit();
    }

    private void login() {
        System.out.println("Please enter your username :");
        String username = sc.nextLine();
        System.out.println("Please enter your password :");
        String password = PasswordUtil.getSHA(sc.nextLine());
        User user;
        // find admin object by username and password
        user = new AdminDao().authenticate(username, password);
        if (user == null) {
            // find customer object by username and password
            user = new CustomerDao().authenticate(username, password);
        }
        if (user != null) {
            Application.user = user;
            System.out.println("Welcome " + user.getName() + ", You are successfully logged in.");
            new HomeController();
        } else {
            System.out.println(">Incorrect username or password");
        }
    }

    private void register() {
        //TODO: register
        System.out.println("Please enter register type (admin/customer) :");
        String type = sc.nextLine();
        if (type.trim().equalsIgnoreCase("admin")) {
            System.out.println("Please Enter admin register code");
            String code = sc.nextLine();
            if (!code.equals(ADMIN_REGISTER_CODE)) {
                System.out.println("Wrong admin register code!");
                return;
            }
            registerAdmin();
        } else if (type.trim().equalsIgnoreCase("customer")) {
            registerCustomer();
        } else {
            System.out.println("Wrong input!");
        }
    }

    private void registerCustomer(){
        CustomerDao dao = new CustomerDao();
        System.out.println("Please enter username : ");
        String username = sc.nextLine();
        //TODO: validation
        if (dao.exists(username)) {
            System.out.println("Username has already been taken");
            registerCustomer();
        }
        System.out.println("Please enter password : ");
        String password = PasswordUtil.getSHA(sc.nextLine());
        System.out.println("Please enter your name : ");
        String name = sc.nextLine();
        System.out.println("Please enter your IC Number : ");
        String ic = sc.nextLine();
        //TODO: IC Validation
        System.out.println("Please enter your address : ");
        String address = sc.nextLine();
        //TODO: addr Validation
        System.out.println("Please enter your contact number : ");
        String contactNum = sc.nextLine();
        //TODO: Contact num Validation
        dao.add(new Customer(UUID.randomUUID().toString(),name,username,password,ic,address,contactNum));
        System.out.println("Registered successfully, please login");
    }

    private void registerAdmin(){
        AdminDao dao = new AdminDao();
        System.out.println("Please enter username : ");
        String username = sc.nextLine();
        //TODO: validation
        if (dao.exists(username)) {
            System.out.println("Username has already been taken");
            registerAdmin();
        }
        System.out.println("Please enter password : ");
        String password = PasswordUtil.getSHA(sc.nextLine());
        System.out.println("Please enter your name : ");
        String name = sc.nextLine();
        dao.add(new Admin(UUID.randomUUID().toString(),name,username,password));
        System.out.println("Registered successfully, please login");
    }



}




