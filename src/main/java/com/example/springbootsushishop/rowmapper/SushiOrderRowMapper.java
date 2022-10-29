package com.example.springbootsushishop.rowmapper;

import com.example.springbootsushishop.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SushiOrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setStatusId(rs.getInt("status_id"));
        order.setSushiId(rs.getInt("sushi_id"));
        order.setCreatedAt(rs.getTimestamp("createdAt"));
        order.setLastUpdatedAt(rs.getTimestamp("lastUpdatedAt"));
        return order;
    }
}
