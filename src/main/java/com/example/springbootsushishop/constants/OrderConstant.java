package com.example.springbootsushishop.constants;

public class OrderConstant {

    // Order Status
    public static final String CREATED_STATUS = "created";
    public static final String IN_PROGRESS_STATUS = "in-progress";
    public static final String PAUSED_STATUS = "paused";
    public static final String FINISHED_STATUS = "finished";
    public static final String CANCELLED_STATUS = "cancelled";

    // Order Msg
    public static final String ORDER_MSG_ORDER_CREATED = "Order created";
    public static final String ORDER_MSG_ORDER_CANCELLED = "Order cancelled";
    public static final String ORDER_MSG_ORDER_PAUSED = "Order paused";
    public static final String ORDER_MSG_ORDER_RESUMED = "Order resumed";

    // Order Error Msg
    public static final String ORDER_MSG_PRODUCT_NOT_EXIST = "The product does not exist.";
    public static final String ORDER_MSG_ORDER_NOT_FOUND = "Order not found";


}
