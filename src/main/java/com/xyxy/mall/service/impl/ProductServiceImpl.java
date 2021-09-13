package com.xyxy.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.pojo.Product;
import com.xyxy.mall.mapper.ProductMapper;
import com.xyxy.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Override
    public Page<Map<String, Object>> selectListPage(int current, int number, String id) {
        Page<Map<String,Object>> page =new Page<Map<String,Object>>(current,number);
        return page.setRecords(this.baseMapper.getAllCartproduct(page,id));
    }
}
