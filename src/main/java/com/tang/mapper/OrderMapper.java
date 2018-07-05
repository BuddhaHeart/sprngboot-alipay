package com.tang.mapper;

import com.tang.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void save(Order order);

    Order getOrderById(Integer orderId);
}
