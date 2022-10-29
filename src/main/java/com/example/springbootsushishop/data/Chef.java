package com.example.springbootsushishop.data;

import com.example.springbootsushishop.dao.SushiOrderRepo;
import com.example.springbootsushishop.dao.SushiRepo;
import com.example.springbootsushishop.model.Order;
import com.example.springbootsushishop.model.Sushi;
import com.example.springbootsushishop.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Chef implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Chef.class);

    private String name;

    private OrderQueue orderQueue;

    private OrderService orderService;

    public Chef(OrderQueue orderQueue, OrderService orderService, String name){
        this.orderQueue = orderQueue;
        this.orderService = orderService;
        this.name = name;
    }

    @Override
    public void run() {
        while(true){
            try{
                if (orderQueue.getQueue().peek() != null){
                    Order order = orderQueue.take();
                    doOrder(order);
                }else{
                    //System.out.println("Wait Order");
                }
                TimeUnit.SECONDS.sleep(1L);
            }catch (InterruptedException e){
                System.out.println("Chef was interrupted");
            }
        }
    }

    public void doOrder(Order order) throws InterruptedException {
        Sushi sushi = orderService.getSushiById(order.getSushiId());
        orderService.progressOrder(order.getId());
        logger.info(String.format("%s, orderId: %s, sushi: %s, in-progress", this.name, order.getId(), sushi.getName()));
        doSushi(sushi);
        orderService.finishOrder(order.getId());
        logger.info(String.format("%s, orderId: %s, sushi: %s, finished", this.name, order.getId(), sushi.getName()));
    }

    public void doSushi(Sushi sushi) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(Long.valueOf(sushi.getTimeToMake()));
        int ss = sushi.getTimeToMake()/10;
        int i = 1;
        while (ss > 0){
            TimeUnit.SECONDS.sleep(1L);
            logger.info(String.valueOf(i));
            ss--;
            i++;
        }
    }


}
