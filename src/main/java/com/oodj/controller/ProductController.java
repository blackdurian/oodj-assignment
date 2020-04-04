package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.ProductDao;
import com.oodj.model.Admin;
import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProductController {
    private Scanner sc = new Scanner(System.in);
    private ProductDao productDao = new ProductDao();

    public ProductController() {
        Menu.getInstance().clear();
        Menu.getInstance().addItem(new MenuItem("Search"
                , new String[]{"s", "search"}
                , new MenuEvent() {
            @Override
            public void execute() {
                System.out.println("Search :");
                String value = sc.nextLine();
                if (!value.trim().isEmpty()){
                    List<Product> products = productDao.search(value);
                    if (products.size()>0){
                        new ProductSearchController(products);
                    }else {
                        System.out.printf("Your Search - %s - did not match any products%n", value);
                        Menu.getInstance().display();
                    }
                }else {
                    Menu.getInstance().display();
                }
            }
        }));

        Menu.getInstance().addItem(new MenuItem("All Products"
                , new String[]{"all", "all product"}
                , new MenuEvent() {
            @Override
            public void execute() {
                List<Product> products = productDao.findAll();
                if (products.size()>0){
                    new ProductSearchController(products);
                }else {
                    System.out.println("No available product.");
                    Menu.getInstance().display();
                }
            }
        }));
        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Add Product"
                    , new String[]{"add", "add product"}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    addProduct();
                }
            }));

        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new HomeController();
            }
        }));
        Menu.getInstance().display();

    }

    private void addProduct() {
        //TODO: validation
        String id = UUID.randomUUID().toString();
        // Product Name
        System.out.println("Name:");
        String name = sc.nextLine();
        // Product Price
        System.out.println("Price:");
        double price = sc.nextDouble();
        // Product Rate 1 to 5 only
        System.out.println("Rate (1 - 5):");
        int rate = sc.nextInt();
        // Add Product to txt file
        Product product = new Product(id,name,price,rate);
        productDao.add(product);
        // Display this Menu
        Menu.getInstance().display();
    }


}
