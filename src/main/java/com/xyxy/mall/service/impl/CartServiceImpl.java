package com.xyxy.mall.service.impl;

import com.xyxy.mall.pojo.Cart;
import com.xyxy.mall.mapper.CartMapper;
import com.xyxy.mall.service.ICartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
