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
        Menu.getInstance().setHeader("Search Result");
        Menu.getInstance().addItem(mappingMenuItem(products));
        Menu.getInstance().addItem(new MenuItem("Back"
                , new String[]{"b", "back"}
                , ProductController::new));
        Menu.getInstance().display();
    }

    private List<MenuItem> mappingMenuItem(List<Product> products) {
        List<MenuItem> results = new ArrayList<>();
        for (Product product : products) {
            results.add(new MenuItem(
                    product.getName() + "\t\t Price:\t" + product.getPrice()
                    , new String[]{product.getName()}
                    , () -> new ProductDetailController(product)));
        }
        return results;
    }
}
