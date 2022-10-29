package com.example.springbootsushishop.service.impl;

import com.example.springbootsushishop.dao.SushiOrderRepo;
import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;
import com.example.springbootsushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SushiOrderRepo sushiOrderRepo;

    @Override
    public Integer createOrder(OrderRequest orderRequest) {
        return sushiOrderRepo.createOrder(orderRequest);
    }

    @Override
    public Order getOrderById(Integer id) {
        return sushiOrderRepo.getOrderById(id);
    }

    @Override
    public void cancelOrder(Integer id) {
        sushiOrderRepo.cancelOrder(id);
    }

}
