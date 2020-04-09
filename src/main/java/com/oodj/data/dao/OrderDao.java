package com.oodj.data.dao;

import com.oodj.data.JsonUtil;
import com.oodj.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OrderDao implements Dao<Order, String> {
    private static final String PATH = "src\\main\\resources\\Order.txt";
    private static List<Order> orderRepository = new ArrayList<>();
    private JsonUtil<Order> jsonUtil = new JsonUtil<>();


    public OrderDao() {
        orderRepository = findAll();
    }

    @Override
    public void add(Order order) {
        orderRepository.add(order);
        jsonUtil.serialize(orderRepository,PATH);
    }


    @Override
    public void delete(String id) {
        orderRepository.removeIf(order -> order.getId().equals(id));
        jsonUtil.serialize(orderRepository,PATH);
    }

    @Override
    public void deleteAll() {
        orderRepository = new ArrayList<>();
        jsonUtil.serialize(orderRepository,PATH);
    }

    @Override
    public Order findOne(String id) {
        for (Order order : orderRepository){
            if (order.getId().equals(id)){
                return order;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String id) {
        for (Order order : orderRepository){
            if (order.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        return jsonUtil.deserialize(PATH, Order.class);
    }


    public List<Order> findByUser(String userId) {
        return orderRepository.stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return orderRepository.size();
    }

    @Override
    public void update(Order order) {
        for (int i=0; i< orderRepository.size(); i++){
            if (orderRepository.get(i).equals(order)){
                orderRepository.set(i,order);
            }
        }
        jsonUtil.serialize(orderRepository,PATH);
    }


    public List<Order> search(String text) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderRepository) {
            if (order.toString().contains(text)) {
                result.add(order);
            }
        }
        return result;
    }

    public List<Order> search(String text, String userId) {
        return orderRepository.stream()
                .filter(order -> order.getUserId().equals(userId) && order.toString()
                        .contains(text))
                .collect(Collectors.toList());
    }
}
