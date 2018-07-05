package com.tang.model;

import lombok.Data;

import java.util.Date;
@Data
public class Order {
    private String id;

    private String orderNum;

    private String orderStatus;

    private String orderAmount;

    private String paidAmount;

    private String productId;

    private Integer buyCounts;

    private Date createTime;

    private Date paidTime;

}
