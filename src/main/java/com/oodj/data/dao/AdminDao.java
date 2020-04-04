package com.oodj.data.dao;

import com.oodj.data.JsonUtil;

import com.oodj.model.Admin;
import java.util.ArrayList;
import java.util.List;


public class AdminDao implements Dao<Admin, String> {
    private static final String PATH = "src\\main\\resources\\Admin.txt";
    private static List<Admin> adminRepository = new ArrayList<>();
    private JsonUtil<Admin> jsonUtil = new JsonUtil<>();


    public AdminDao() {
        adminRepository = findAll();
    }

    @Override
    public void add(Admin admin) {
        adminRepository.add(admin);
        jsonUtil.serialize(adminRepository,PATH);
    }


    @Override
    public void delete(String id) {
        adminRepository.removeIf(admin -> admin.getId().equals(id));
        jsonUtil.serialize(adminRepository,PATH);
    }

    @Override
    public void deleteAll() {
        adminRepository = new ArrayList<>();
        jsonUtil.serialize(adminRepository,PATH);
    }

    @Override
    public Admin findOne(String id) {
        for (Admin admin : adminRepository){
            if (admin.getId().equals(id)){
                return admin;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String id) {
        for (Admin admin : adminRepository){
            if (admin.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Admin> findAll() {
        return jsonUtil.deserialize(PATH, Admin.class);
    }

    @Override
    public long count() {
        return adminRepository.size();
    }

    @Override
    public void update(Admin admin) {
        for (int i=0; i<adminRepository.size(); i++){
            if (adminRepository.get(i).equals(admin)){
                adminRepository.set(i,admin);
            }
        }
        jsonUtil.serialize(adminRepository,PATH);
    }

    public Admin getAdmin() {
        System.out.println(adminRepository.toString());
        System.out.println(adminRepository.getClass());
        System.out.println(adminRepository.get(0));
        return adminRepository.get(0);
    }


    public Admin authenticate(String username, String password) {
        for (Admin admin : adminRepository){
            if (admin.getUsername().equals(username)&&admin.getPassword().equals(password)){
                return admin;
            }
        }
        return null;
    }

}
