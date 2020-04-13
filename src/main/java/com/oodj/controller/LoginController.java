package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.AdminDao;
import com.oodj.data.dao.CustomerDao;
import com.oodj.data.dao.Dao;
import com.oodj.model.Admin;
import com.oodj.model.Customer;
import com.oodj.model.User;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.UUID;

//@MenuRequest("login")
public class LoginController {

    public LoginController() {
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Welcome");
        Menu.getInstance().addItem(new MenuItem("Login"
                , new String[]{"login", "log-in"}
                , () -> login()));

        Menu.getInstance().addItem(new MenuItem(
                "Register"
                , new String[]{"register", "sign-up"}
                , () -> register()));

        Menu.getInstance().addItem(new MenuItem("Exit"
                , new String[]{"exit", "close"}
                , () -> {
            System.out.println("Exit");
            Application.exit();
        }));
        Menu.getInstance().display();
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username :");
        String username = sc.nextLine();
        System.out.println("Please enter your password :");
        String password = sc.nextLine();
        //TODO: encrypted password

        User user; // find admin object by username and password
        user = new AdminDao().authenticate(username, getSHA(password));
        if (user == null) {
            user = new CustomerDao().authenticate(username,
                    getSHA(password)); // find customer object by username and password
        }
        if (user != null) {
            Application.user = user;
            System.out.println("Welcome " + user.getName() + ", You are successfully logged in.");
            new HomeController();
            // Menu.getInstance().respond("home");
        } else {
            System.out.println(">Incorrect username or password");
        }
    }

    private void register() {
        //TODO: register
        final String ADMIN_REGISTER_CODE = "0000";
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter register type (admin/customer) :");
        String type = sc.nextLine();
        Dao dao;
        User user;
        if (type.trim().equalsIgnoreCase("admin")) {
            System.out.println("Please Enter Admin register code");
            String code = sc.nextLine();
            if (!code.equals(ADMIN_REGISTER_CODE)) {
                return;
            }
            dao = new AdminDao();
            user = new Admin();
        } else if (type.trim().equalsIgnoreCase("customer")) {
            dao = new CustomerDao();
            user = new Customer();
        } else {
            System.out.println("Wrong input!");
            return;
        }
        System.out.println("Please enter username : ");
        String username = sc.nextLine();
        //TODO: validation
        if (!dao.exists(username)) {
            user.setUsername(username);
        } else {
            System.out.println("Username has already been taken");
            return;
        }
        System.out.println("Please enter password : ");
        String password = sc.nextLine();
        user.setPassword(getSHA(password));
        System.out.println("Please enter your name : ");
        String name = sc.nextLine();
        user.setName(name);
        user.setId(UUID.randomUUID().toString());
        dao.add(user);
        System.out.println("Registered successfully. please login!");
        /*        Admin admin = new AdminDao().getAdmin();
        System.out.println(admin.getName());*/
    }


    private String getSHA(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(
                    "SHA-256");   // Static getInstance method is called with hashing SHA
            byte[] messageDigest = md.digest(input.getBytes()); // and return array of byte
            BigInteger no = new BigInteger(1,
                    messageDigest);  // Convert byte array into signum representation
            String hashtext = no.toString(16);// Convert message digest into hex value
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    @RequestMapping
    private void login(List<MenuItem> items) {
        items.add(new MenuItem(
                "Login"
                , new String[]{"1", "login", "log-in"}
               ));
    if (authenticate){
        return"home";
    }else {
        return "";
    }

    }*/

}




