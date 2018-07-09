package com.tang.service.Impl;

import com.tang.common.OrderStatusEnum;
import com.tang.mapper.OrderMapper;
import com.tang.model.Order;
import com.tang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void insert(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public void updateOrderStatus(String out_trade_no) {
        Order order = orderMapper.getOrderByTradeNum(out_trade_no);
        if (order.getOrderStatus().equals(OrderStatusEnum.WAIT_PAY.getValue())){
            order.setOrderStatus(OrderStatusEnum.PAID.getValue());
            order.setPaidTime(new Date());
        }

    }

    @Override
    public Order getOrderByTradeNum(String out_trade_no) {
        return orderMapper.getOrderByTradeNum(out_trade_no);
    }
}
