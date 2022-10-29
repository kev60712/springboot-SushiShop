package com.example.springbootsushishop.dto;

import com.example.springbootsushishop.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderStatus {
    private List<OrderInfo> inProgress;
    private List<OrderInfo> created;
    private List<OrderInfo> paused;
    private List<OrderInfo> cancelled;
    private List<OrderInfo> completed;

    public OrderStatus(List<Order> orderList){
        inProgress = new ArrayList<>();
        created = new ArrayList<>();
        paused = new ArrayList<>();
        cancelled = new ArrayList<>();
        completed = new ArrayList<>();
        Long diffSec;
        for (Order order: orderList){
            OrderInfo orderInfo = new OrderInfo();
            diffSec = (System.currentTimeMillis() - order.getCreatedAt().getTime())/1000;
            orderInfo.setTimeSpent(diffSec.intValue());
            orderInfo.setOrderId(order.getId());
            if (order.getStatusId() == 1){
                created.add(orderInfo);
            }else if (order.getStatusId() == 2){
                inProgress.add(orderInfo);
            }else if (order.getStatusId() == 3){
                paused.add(orderInfo);
            }else if (order.getStatusId() == 4){
                diffSec = (order.getLastUpdatedAt().getTime() - order.getCreatedAt().getTime())/1000;
                orderInfo.setTimeSpent(diffSec.intValue());
                completed.add(orderInfo);
            }else if (order.getStatusId() == 5){
                diffSec = (order.getLastUpdatedAt().getTime() - order.getCreatedAt().getTime())/1000;
                orderInfo.setTimeSpent(diffSec.intValue());
                cancelled.add(orderInfo);
            }
        }
    }
}
