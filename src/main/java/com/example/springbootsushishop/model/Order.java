package com.example.springbootsushishop.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Order {
    private Integer id;
    private Integer statusId;
    private Integer sushiId;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}

