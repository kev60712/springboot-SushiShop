package com.example.springbootsushishop.repo;

import com.example.springbootsushishop.data.dto.OrderRequest;
import com.example.springbootsushishop.data.model.Order;

import java.util.List;

public interface SushiOrderRepo {

    Order findOrderById(Integer id);

    Integer createOrder(OrderRequest orderRequest);

    void updateOrder(Integer id, String status);

    List<Order> findAllOrder();

}
