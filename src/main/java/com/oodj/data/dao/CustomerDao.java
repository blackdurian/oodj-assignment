package com.oodj.data.dao;

import com.oodj.data.JsonUtil;
import com.oodj.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements Dao<Customer, String> {
    private static final String PATH = "src\\main\\resources\\Customer.txt";
    private static List<Customer> customerRepository = new ArrayList<>();
    private JsonUtil<Customer> jsonUtil = new JsonUtil<>();


    public CustomerDao() {
        customerRepository = findAll();
    }

    @Override
    public void add(Customer customer) {
        customerRepository.add(customer);
        jsonUtil.serialize(customerRepository,PATH);
    }


    @Override
    public void delete(String id) {
        customerRepository.removeIf(customer -> customer.getId().equals(id));
        jsonUtil.serialize(customerRepository,PATH);
    }

    @Override
    public void deleteAll() {
        customerRepository = new ArrayList<>();
        jsonUtil.serialize(customerRepository,PATH);
    }

    @Override
    public Customer findOne(String id) {
        for (Customer customer : customerRepository){
            if (customer.getId().equals(id)){
                return customer;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String username) {
        for (Customer customer : customerRepository){
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Customer> findAll() {
        return jsonUtil.deserialize(PATH, Customer.class);
    }

    @Override
    public long count() {
        return customerRepository.size();
    }

    @Override
    public void update(Customer customer) {
        for (int i=0; i<customerRepository.size(); i++){
            if (customerRepository.get(i).equals(customer)){
                customerRepository.set(i,customer);
            }
        }
        jsonUtil.serialize(customerRepository,PATH);
    }

    public List<Customer> search(String value) {
        List<Customer>result = new ArrayList<>();
        for (Customer customer : customerRepository){
            if (customer.getName().contains(value)){
                result.add(customer);
            }
        }
        return result;
    }

    public Customer authenticate(String username, String password) {
        for (Customer customer : customerRepository){
            if (customer.getUsername().equals(username)&&customer.getPassword().equals(password)){
                return customer;
            }
        }
        return null;
    }

}
