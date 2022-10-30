package com.example.springbootsushishop.service.impl;

import com.example.springbootsushishop.constants.OrderConstant;
import com.example.springbootsushishop.repo.SushiOrderRepo;
import com.example.springbootsushishop.repo.SushiRepo;
import com.example.springbootsushishop.data.dto.OrderRequest;
import com.example.springbootsushishop.data.model.Order;
import com.example.springbootsushishop.data.model.Sushi;
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
        sushiOrderRepo.updateOrder(id, OrderConstant.CANCELLED_STATUS);
    }

    @Override
    public void finishOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, OrderConstant.FINISHED_STATUS);
    }

    @Override
    public void pauseOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, OrderConstant.PAUSED_STATUS);
    }

    @Override
    public void progressOrder(Integer id) {
        sushiOrderRepo.updateOrder(id, OrderConstant.IN_PROGRESS_STATUS);
    }

    @Override
    public List<Order> getAllOrder() {
        return sushiOrderRepo.findAllOrder();
    }

    @Override
    public Sushi getSushiById(Integer id) {
        return sushiRepo.getSushiById(id);
    }

    @Override
    public Sushi getSushiByName(String name) {
        return sushiRepo.getSushiByName(name);
    }

}
