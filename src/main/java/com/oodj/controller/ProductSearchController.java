package com.oodj.controller;

import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuEvent;
import com.oodj.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchController {

    public ProductSearchController(List<Product> products) {
        Menu.getInstance().clear();
        for (Product product:products){
            Menu.getInstance().addItem(new MenuItem(
                    product.getName() + "\t\t Price:\t" + product.getPrice()
                    , new String[]{product.getName()}
                    , new MenuEvent() {
                @Override
                public void execute() {
                    new ProductDetailController(product);
                }
            }));
        }

        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , new MenuEvent() {
            @Override
            public void execute() {
                new ProductController();
            }
        }));
        Menu.getInstance().display();
    }
}
