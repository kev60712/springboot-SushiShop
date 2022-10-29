package com.example.springbootsushishop.service;

import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Order;
import com.example.springbootsushishop.model.Sushi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderService {

    Integer createOrder(OrderRequest orderRequest);

    Order getOrderById(Integer id);

    void cancelOrder(Integer id);

    void finishOrder(Integer id);

    void pauseOrder(Integer id);

    void progressOrder(Integer id);

    List<Order> getAllOrder();

    Sushi getSushiById(Integer sushiId);
}
