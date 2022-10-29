package com.example.springbootsushishop.dao;

import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;

public interface SushiOrderRepo {

    Order getOrderById(Integer id);

    Integer createOrder(OrderRequest orderRequest);

    void cancelOrder(Integer id);

}
