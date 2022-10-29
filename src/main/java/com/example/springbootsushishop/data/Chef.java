package com.example.springbootsushishop.data;

import com.example.springbootsushishop.data.model.Order;
import com.example.springbootsushishop.data.model.Sushi;
import com.example.springbootsushishop.service.OrderService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Chef implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Chef.class);

    private String name;

    private OrderQueue orderQueue;

    private OrderService orderService;

    private String status;

    private Integer currentOrderId;

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
                    this.status = "in-progress";
                    this.currentOrderId = order.getId();
                    doOrder(order);
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
        if (isCancelled(this.status)){
            logger.info(String.format("%s, orderId: %s, sushi: %s, cancelled", this.name, order.getId(), sushi.getName()));
            return;
        }
        orderService.finishOrder(order.getId());
        logger.info(String.format("%s, orderId: %s, sushi: %s, finished", this.name, order.getId(), sushi.getName()));
    }

    public void doSushi(Sushi sushi) throws InterruptedException {
        int timeToMake = sushi.getTimeToMake();
        int spendTime = 1;
        while (timeToMake >= spendTime){
            TimeUnit.SECONDS.sleep(1L);
            spendTime++;
            if (isCancelled(this.status)){
                break;
            }
        }
    }

    public boolean isCancelled(String status){
        return status.equalsIgnoreCase("cancelled");
    }


}
