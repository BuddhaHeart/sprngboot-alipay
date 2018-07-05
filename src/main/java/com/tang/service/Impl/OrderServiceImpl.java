package com.tang.service.Impl;

import com.tang.mapper.OrderMapper;
import com.tang.model.Order;
import com.tang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void insert(Order order) {
        orderMapper.save(order);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
