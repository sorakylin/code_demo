package com.skypyb.demo.sharding.order.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.skypyb.demo.sharding.order.dao.OrderMapper;
import com.skypyb.demo.sharding.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

}
