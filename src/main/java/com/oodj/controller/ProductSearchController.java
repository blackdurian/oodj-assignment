package com.oodj.controller;

import com.oodj.model.Product;
import com.oodj.view.Menu;
import com.oodj.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

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
                    StringUtils.rightPad(product.getName(), 30, " ") + " Price:\t" + product
                            .getPrice()
                    , new String[]{product.getName()}
                    , () -> new ProductDetailController(product)));
        }
        return results;
    }
}
