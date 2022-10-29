package com.example.springbootsushishop.dao.impl;

import com.example.springbootsushishop.dao.StatusRepo;
import com.example.springbootsushishop.dao.SushiOrderRepo;
import com.example.springbootsushishop.dao.SushiRepo;
import com.example.springbootsushishop.dto.OrderRequest;
import com.example.springbootsushishop.model.Status;
import com.example.springbootsushishop.model.Sushi;
import com.example.springbootsushishop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.example.springbootsushishop.rowmapper.SushiOrderRowMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SushiOrderRepoImpl implements SushiOrderRepo {

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private SushiRepo sushiRepo;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer id) {
        String sql = "SELECT id, status_id, sushi_id, createdAt FROM sushi_order WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, params, new SushiOrderRowMapper());

        if (orderList.size() > 0){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createOrder(OrderRequest orderRequest) {
        String sql = "INSERT INTO sushi_order (status_id, sushi_id, createdAt) " +
                "VALUES ( :statusId, :sushiId, :createdAt )";

        Status status = statusRepo.getStatusByName("created");
        Sushi sushi = sushiRepo.getSushiByName(orderRequest.getSushiName());

        Map<String, Object> params = new HashMap<>();
        params.put("statusId", status.getId());
        params.put("sushiId", sushi.getId());
        params.put("createdAt", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[] { "id" });

        return keyHolder.getKey().intValue();
    }

    @Override
    public void cancelOrder(Integer id) {
        String sql = "UPDATE sushi_order SET status_id = :statusId WHERE id = :id";
        Status status = statusRepo.getStatusByName("cancelled");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("statusId", status.getId());

        namedParameterJdbcTemplate.update(sql, params);
    }

}
