package com.example.springbootsushishop.service.impl;

import com.example.springbootsushishop.dao.SushiOrderRepo;
import com.example.springbootsushishop.dao.SushiRepo;
import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;
import com.example.springbootsushishop.model.Sushi;
import com.example.springbootsushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SushiOrderRepo sushiOrderRepo;

    @Autowired
    private SushiRepo sushiRepo;

    @Override
    public Integer createOrder(OrderRequest orderRequest) {
        return sushiOrderRepo.createOrder(orderRequest);
    }

    @Override
    public Order getOrderById(Integer id) {
        return sushiOrderRepo.findOrderById(id);
    }

    @Override
    public void cancelOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, "cancelled");
    }

    @Override
    public void finishOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, "finished");
    }

    @Override
    public void pauseOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, "paused");
    }

    @Override
    public void progressOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, "in-progress");
    }

    @Override
    public List<Order> getAllOrder() {
        return sushiOrderRepo.findAllOrder();
    }

    @Override
    public Sushi getSushiById(Integer id) {
        return sushiRepo.getSushiById(id);
    }

}
