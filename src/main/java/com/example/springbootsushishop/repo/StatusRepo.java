package com.example.springbootsushishop.repo;

import com.example.springbootsushishop.data.model.Status;

public interface StatusRepo {

    Status getStatusByName(String name);
}
