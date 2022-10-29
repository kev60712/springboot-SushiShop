package com.example.springbootsushishop.data;

import com.example.springbootsushishop.model.Order;
import com.example.springbootsushishop.model.Sushi;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Getter
@Setter
public class OrderQueue {

    private final BlockingQueue<Order> queue = new ArrayBlockingQueue<Order>(3);

    public void put(Order order) throws InterruptedException {
        queue.put(order);
    }

    public Order take() throws InterruptedException {
        return queue.take();
    }
}
