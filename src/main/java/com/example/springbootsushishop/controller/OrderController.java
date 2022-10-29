package com.example.springbootsushishop.controller;

import com.example.springbootsushishop.constants.Constant;
import com.example.springbootsushishop.data.Chef;
import com.example.springbootsushishop.data.OrderQueue;
import com.example.springbootsushishop.data.dto.OrderRequest;
import com.example.springbootsushishop.data.dto.OrderResponse;
import com.example.springbootsushishop.data.dto.OrderStatus;
import com.example.springbootsushishop.data.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootsushishop.service.OrderService;

import java.util.ArrayList;
import java.util.List;


@RestController
public class OrderController {

    private List<Chef> chefList = new ArrayList<>();

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderQueue orderQueue;

    public void addChef(Chef chef){
        chefList.add(chef);
    }

    public Chef findChefByOrderId(Integer id){
        for (Chef chef: chefList){
            if (chef.getCurrentOrderId() == id){
                return chef;
            }
        }
        return null;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest OrderRequest) throws InterruptedException {
        Integer id = orderService.createOrder(OrderRequest);
        Order order = orderService.getOrderById(id);

        orderQueue.put(order);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder(order);
        orderResponse.setCode(0);
        orderResponse.setMsg(Constant.ORDER_MSG_ORDER_CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Object> cancelOrder(@PathVariable(required = true) Integer orderId){
        // Check Order Exist
        Order order = orderService.getOrderById(orderId);
        if (order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Stop Chef
        Chef chef = findChefByOrderId(orderId);
        chef.setStatus("cancelled");

        // Cancel Order
        orderService.cancelOrder(orderId);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCode(0);
        orderResponse.setMsg(Constant.ORDER_MSG_ORDER_CANCELLED);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/orders/status")
    public ResponseEntity<OrderStatus> displayOrdersStatus(){
        List<Order> orderList = orderService.getAllOrder();
        OrderStatus orderStatus = new OrderStatus(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(orderStatus);
    }

    // Bonus
    @PutMapping("/orders/{orderId}/pause")
    public ResponseEntity<Object> pauseAnOrder(@PathVariable String orderId){
        return null;
    }

    @PutMapping("/orders/{orderId}/resume")
    public ResponseEntity<Object> resumeAnOrder(@PathVariable String orderId){
        return null;
    }

}
