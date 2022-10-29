package com.example.springbootsushishop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import com.example.springbootsushishop.model.Order;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private Order order;
    private Integer code;
    private String msg;
}
