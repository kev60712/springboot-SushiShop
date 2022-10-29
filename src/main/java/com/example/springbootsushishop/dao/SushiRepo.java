package com.example.springbootsushishop.dao;

import com.example.springbootsushishop.model.Sushi;

public interface SushiRepo {

    Sushi getSushiByName(String name);

    Sushi getSushiById(Integer id);

}
