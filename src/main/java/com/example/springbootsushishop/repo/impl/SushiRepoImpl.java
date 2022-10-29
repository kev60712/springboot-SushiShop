package com.example.springbootsushishop.repo.impl;

import com.example.springbootsushishop.repo.SushiRepo;
import com.example.springbootsushishop.data.model.Sushi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.example.springbootsushishop.data.model.rowmapper.SushiRowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SushiRepoImpl implements SushiRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Sushi getSushiByName(String name) {
        String sql = "SELECT id, name, time_to_make From sushi WHERE name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        List<Sushi> sushiList = namedParameterJdbcTemplate.query(sql, params, new SushiRowMapper());

        if (sushiList.size() > 0){
            return sushiList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Sushi getSushiById(Integer id) {
        String sql = "SELECT id, name, time_to_make From sushi WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<Sushi> sushiList = namedParameterJdbcTemplate.query(sql, params, new SushiRowMapper());

        if (sushiList.size() > 0){
            return sushiList.get(0);
        }else{
            return null;
        }
    }
}
