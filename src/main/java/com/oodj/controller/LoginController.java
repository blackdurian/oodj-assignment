package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.AdminDao;
import com.oodj.data.dao.CustomerDao;
import com.oodj.model.User;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

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
        User user = new AdminDao().authenticate(username, password); // find admin object by username and password
        if (user == null) {
            user = new CustomerDao().authenticate(username, password); // find customer object by username and password
        }
        if (user == null) {
            System.out.println(">Incorrect username or password");
        } else {
            Application.user = user;
            new HomeController();
           // Menu.getInstance().respond("home");
        }
    }

    private void register() {
        //TODO: register
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




