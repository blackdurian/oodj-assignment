package com.oodj.data.dao;

import com.oodj.data.JsonUtil;
import com.oodj.model.Product;
import java.util.ArrayList;
import java.util.List;


public class ProductDao implements Dao<Product, String> {
    private static final String PATH = "src\\main\\resources\\Product.txt";
    private static List<Product> productRepository = new ArrayList<>();
    private JsonUtil<Product> jsonUtil = new JsonUtil<>();


    public ProductDao() {
        productRepository = findAll();
    }

    @Override
    public void add(Product product) {
        productRepository.add(product);
        jsonUtil.serialize(productRepository,PATH);
    }


    @Override
    public void delete(String id) {
        productRepository.removeIf(product -> product.getId().equals(id));
        jsonUtil.serialize(productRepository,PATH);
    }

    @Override
    public void deleteAll() {
        productRepository = new ArrayList<>();
        jsonUtil.serialize(productRepository,PATH);
    }

    @Override
    public Product findOne(String id) {
        for (Product product : productRepository){
            if (product.getId().equals(id)){
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String id) {
        for (Product product : productRepository){
            if (product.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> findAll() {
        return jsonUtil.deserialize(PATH, Product.class);
    }

    @Override
    public long count() {
        return productRepository.size();
    }

    @Override
    public void update(Product product) {
        for (int i=0; i< productRepository.size(); i++){
            if (productRepository.get(i).equals(product)){
                productRepository.set(i,product);
            }
        }
        jsonUtil.serialize(productRepository,PATH);
    }

    public List<Product> search(String value) {
        List<Product>result = new ArrayList<>();
        for (Product product : productRepository){
            if (product.getName().contains(value)){
                result.add(product);
            }
        }
        return result;
    }


    public boolean nameExists(String name) {
        for (Product product : productRepository){
            if (product.getId().equals(name)){
                return true;
            }
        }
        return false;
    }
}
