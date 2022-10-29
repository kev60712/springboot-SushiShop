package com.example.springbootsushishop.dao;

import com.example.springbootsushishop.model.Status;

public interface StatusRepo {

    Status getStatusByName(String name);
}
