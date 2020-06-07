package com.skypyb.test.shardingjdbc;

import com.skypyb.demo.sharding.ShardingJDBCApplication;
import com.skypyb.demo.sharding.order.dao.OrderMapper;
import com.skypyb.demo.sharding.order.entity.OrderDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest(classes = ShardingJDBCApplication.class)
@RunWith(SpringRunner.class)
public class ShardingSphereTest {

    @Resource
    private OrderMapper orderMapper;


    @Test
    public void testInsert() {
        OrderDO order = new OrderDO();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setItemId(1L);
        order.setUserId(1L);
        order.setProviderId(1L);
        orderMapper.insertSelective(order);

        OrderDO order2 = new OrderDO();
        order2.setOrderNo(UUID.randomUUID().toString());
        order2.setItemId(2L);
        order2.setUserId(2L);
        order2.setProviderId(2L);
        orderMapper.insertSelective(order2);
    }


    @Test
    public void testSelect() {
        orderMapper.selectAll().forEach(System.out::println);
    }
}