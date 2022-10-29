package com.example.springbootsushishop.service;

import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {

    Integer createOrder(OrderRequest orderRequest);

    Order getOrderById(Integer id);

    void cancelOrder(Integer id);
}
