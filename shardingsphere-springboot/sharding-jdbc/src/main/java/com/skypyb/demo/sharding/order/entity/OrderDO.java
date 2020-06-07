package com.skypyb.demo.sharding.order.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "tb_order")
public class OrderDO {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "provider_id")
    private Long providerId;
}