package com.example.springbootsushishop.dao;

import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;

import java.util.List;

public interface SushiOrderRepo {

    Order findOrderById(Integer id);

    Integer createOrder(OrderRequest orderRequest);

    void updateOrder(Integer id, String status);

    List<Order> findAllOrder();

}
