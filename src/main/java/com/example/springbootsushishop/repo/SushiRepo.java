package com.example.springbootsushishop.repo;

import com.example.springbootsushishop.data.model.Sushi;

public interface SushiRepo {

    Sushi getSushiByName(String name);

    Sushi getSushiById(Integer id);

}
