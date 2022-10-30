package com.example.springbootsushishop.controller;

import com.example.springbootsushishop.constants.OrderConstant;
import com.example.springbootsushishop.data.Chef;
import com.example.springbootsushishop.data.OrderQueue;
import com.example.springbootsushishop.data.dto.OrderRequest;
import com.example.springbootsushishop.data.dto.OrderResponse;
import com.example.springbootsushishop.data.dto.OrderStatusList;
import com.example.springbootsushishop.data.model.Order;
import com.example.springbootsushishop.data.model.Sushi;
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

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest OrderRequest) throws InterruptedException {
        // Check Sushi Exists
        Sushi sushi = orderService.getSushiByName(OrderRequest.getSushiName());
        if (sushi == null){
            OrderResponse orderResponse = createOrderResponse(1, OrderConstant.ORDER_MSG_PRODUCT_NOT_EXIST);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderResponse);
        }

        Integer id = orderService.createOrder(OrderRequest);
        Order order = orderService.getOrderById(id);

        orderQueue.put(order);

        OrderResponse orderResponse = createOrderResponse(0, OrderConstant.ORDER_MSG_ORDER_CREATED);
        orderResponse.setOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Object> cancelOrder(@PathVariable(required = true) Integer orderId){
        // Check Order Exists
        Order order = orderService.getOrderById(orderId);
        if (order == null){
            OrderResponse orderResponse = createOrderResponse(1, OrderConstant.ORDER_MSG_ORDER_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderResponse);
        }
        // Stop Chef
        Chef chef = findChefByOrderId(orderId);
        chef.updateChefStatus(OrderConstant.CANCELLED_STATUS);

        // Cancel Order
        orderService.cancelOrder(orderId);

        OrderResponse orderResponse = createOrderResponse(0, OrderConstant.ORDER_MSG_ORDER_CANCELLED);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/orders/status")
    public ResponseEntity<OrderStatusList> displayOrdersStatus(){
        List<Order> orderList = orderService.getAllOrder();
        OrderStatusList orderStatusList = new OrderStatusList(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(orderStatusList);
    }

    // Bonus
    @PutMapping("/orders/{orderId}/pause")
    public ResponseEntity<Object> pauseAnOrder(@PathVariable Integer orderId){
        // Check Order Exist
        Order order = orderService.getOrderById(orderId);
        if (order == null){
            OrderResponse orderResponse = createOrderResponse(1, OrderConstant.ORDER_MSG_ORDER_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderResponse);
        }
        // Pause Chef
        Chef chef = findChefByOrderId(orderId);
        chef.updateChefStatus(OrderConstant.PAUSED_STATUS);

        // Pause Order
        orderService.pauseOrder(orderId);

        OrderResponse orderResponse = createOrderResponse(0, OrderConstant.ORDER_MSG_ORDER_PAUSED);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @PutMapping("/orders/{orderId}/resume")
    public ResponseEntity<Object> resumeAnOrder(@PathVariable Integer orderId){
        // Check Order Exist
        Order order = orderService.getOrderById(orderId);
        if (order == null){
            OrderResponse orderResponse = createOrderResponse(1, OrderConstant.ORDER_MSG_ORDER_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(orderResponse);
        }
        // Resume Order
        orderService.progressOrder(orderId);

        // Resume Chef
        Chef chef = findChefByOrderId(orderId);
        chef.updateChefStatus(OrderConstant.IN_PROGRESS_STATUS);

        OrderResponse orderResponse = createOrderResponse(0, OrderConstant.ORDER_MSG_ORDER_RESUMED);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

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

    public OrderResponse createOrderResponse(Integer code, String msg){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCode(code);
        orderResponse.setMsg(msg);
        return orderResponse;
    }
}
