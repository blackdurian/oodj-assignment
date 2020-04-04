package com.oodj.data.dao;

import com.oodj.data.JsonUtil;
import com.oodj.model.Category;
import java.util.ArrayList;
import java.util.List;


public class CategoryDao implements Dao<Category, String> {
    private static final String PATH = "src\\main\\resources\\Category.txt";
    private static List<Category> categoryRepository = new ArrayList<>();
    private JsonUtil<Category> jsonUtil = new JsonUtil<>();


    public CategoryDao() {
        categoryRepository = findAll();
    }

    @Override
    public void add(Category category) {
        categoryRepository.add(category);
        jsonUtil.serialize(categoryRepository,PATH);
    }


    @Override
    public void delete(String id) {
        categoryRepository.removeIf(category -> category.getId().equals(id));
        jsonUtil.serialize(categoryRepository,PATH);
    }

    @Override
    public void deleteAll() {
        categoryRepository = new ArrayList<>();
        jsonUtil.serialize(categoryRepository,PATH);
    }

    @Override
    public Category findOne(String id) {
        for (Category admin : categoryRepository){
            if (admin.getId().equals(id)){
                return admin;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String id) {
        for (Category admin : categoryRepository){
            if (admin.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Category> findAll() {
        return jsonUtil.deserialize(PATH, Category.class);
    }

    @Override
    public long count() {
        return categoryRepository.size();
    }

    @Override
    public void update(Category category) {
        for (int i=0; i< categoryRepository.size(); i++){
            if (categoryRepository.get(i).equals(category)){
                categoryRepository.set(i,category);
            }
        }
        jsonUtil.serialize(categoryRepository,PATH);
    }






}
