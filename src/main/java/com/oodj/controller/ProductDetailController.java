package com.oodj.controller;

import com.oodj.Application;
import com.oodj.data.dao.ProductDao;
import com.oodj.model.Admin;
import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.util.Scanner;

public class ProductDetailController {
    private Product product;
    private final ProductDao productDao = new ProductDao();
    private final Scanner sc = new Scanner(System.in);
    private final String successMessage = " Success!";

    public ProductDetailController(Product product) {
        this.product = product;
        Menu.getInstance().clear();
        Menu.getInstance().setHeader("Product Detail");
        Menu.getInstance().setTopMessage(getProductDetail());
        if (Application.user instanceof Admin) {
            Menu.getInstance().addItem(new MenuItem("Edit Name"
                    , new String[]{"edit name", "update name"}
                    , this::editName));

            Menu.getInstance().addItem(new MenuItem("Edit Price"
                    , new String[]{"edit price","update price"}
                    , this::editPrice));

            Menu.getInstance().addItem(new MenuItem("Edit Rate"
                    , new String[]{"edit rate","update rate"}
                    , this::editRate));

            Menu.getInstance().addItem(new MenuItem("Delete Product"
                    , new String[]{"delete","delete product"}
                    , () -> {
                productDao.delete(product.getId());
                new ProductController();
            }));
        }


        Menu.getInstance().addItem(new MenuItem("Add to Cart"
                , new String[]{"add", "add to cart"}
                , () -> {
            ShoppingCartController.addItem(product);
            new ShoppingCartController();
        }));

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"back"}
                , ProductController::new));
        Menu.getInstance().display();
    }

    private String getProductDetail() {
        return String.format("[%s]%n Id:\t%s%n Rate:\t%s%n Price:\t%.2f%n%n"
                , product.getName()
                , product.getId()
                , product.getRate()
                , product.getPrice());
    }

    private void editPrice() {
        System.out.println("New Price:");
        double price = sc.nextDouble();
        if (price>0.0){
            product.setPrice(price);
            productDao.update(product);
            System.out.println(successMessage);
        }
        new ProductDetailController(product);
    }

    private void editName() {
        System.out.println("New Name:");
        String name = sc.nextLine();
        if (!productDao.nameExists(name) ){
            product.setName(name);
            productDao.update(product);
            System.out.println(successMessage);
        }else {
            System.out.println("\""+ name + "\" ,Name has been taken.");
        }
        new ProductDetailController(product);
    }

    private void editRate(){
        System.out.println("New Rate:");
        int rate = sc.nextInt();
        // Product Rate 1 to 5 only
        if (rate >= 1 && rate <=5 ){
            product.setRate(rate);
            productDao.update(product);
            System.out.println(successMessage);
        }
        new ProductDetailController(product);
    }


}
