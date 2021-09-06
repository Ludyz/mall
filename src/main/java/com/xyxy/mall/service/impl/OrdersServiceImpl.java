package com.xyxy.mall.service.impl;

import com.xyxy.mall.pojo.Orders;
import com.xyxy.mall.mapper.OrdersMapper;
import com.xyxy.mall.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
