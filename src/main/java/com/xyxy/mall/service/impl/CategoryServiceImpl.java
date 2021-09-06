package com.xyxy.mall.service.impl;

import com.xyxy.mall.pojo.Category;
import com.xyxy.mall.mapper.CategoryMapper;
import com.xyxy.mall.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
