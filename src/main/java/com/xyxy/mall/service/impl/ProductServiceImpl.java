package com.xyxy.mall.service.impl;

import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.mapper.ProductMapper;
import com.xyxy.mall.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
