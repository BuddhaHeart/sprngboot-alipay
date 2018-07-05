package com.tang.service;

import com.tang.model.Order;

public interface OrderService {
    void insert(Order order);

    Order getOrderById(Integer orderId);
}
