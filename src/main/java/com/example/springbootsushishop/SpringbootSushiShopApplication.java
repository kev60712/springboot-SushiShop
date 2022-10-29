package com.example.springbootsushishop;

import com.example.springbootsushishop.dao.SushiOrderRepo;
import com.example.springbootsushishop.dao.SushiRepo;
import com.example.springbootsushishop.data.Chef;
import com.example.springbootsushishop.data.OrderQueue;
import com.example.springbootsushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringbootSushiShopApplication {

    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init(){
        Chef chef1 = new Chef(orderQueue, orderService, "Chef1");
        Chef chef2 = new Chef(orderQueue, orderService, "Chef2");
        Chef chef3 = new Chef(orderQueue, orderService, "Chef3");
        Thread chef1Thread = new Thread(chef1);
        Thread chef2Thread = new Thread(chef2);
        Thread chef3Thread = new Thread(chef3);
        chef1Thread.start();
        chef2Thread.start();
        chef3Thread.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSushiShopApplication.class, args);
    }

}
