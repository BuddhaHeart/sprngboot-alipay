package com.tang.mapper;

import com.tang.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void insertSelective(Order order);

    Order getOrderById(Integer orderId);

    Order getOrderByTradeNum(String out_trade_no);
}
