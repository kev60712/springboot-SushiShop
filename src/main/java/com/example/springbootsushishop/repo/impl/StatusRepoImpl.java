package com.example.springbootsushishop.repo.impl;

import com.example.springbootsushishop.repo.StatusRepo;
import com.example.springbootsushishop.data.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.example.springbootsushishop.data.model.rowmapper.StatusRowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatusRepoImpl implements StatusRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Status getStatusByName(String name) {
        String sql = "SELECT id, name From status WHERE name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        List<Status> statusList = namedParameterJdbcTemplate.query(sql, params, new StatusRowMapper());

        if (statusList.size() > 0){
            return statusList.get(0);
        }else{
            return null;
        }
    }
}
