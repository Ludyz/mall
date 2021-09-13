package com.xyxy.mall.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyxy.mall.common.lang.Cartproduct;
import com.xyxy.mall.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-09-06
 */
public interface ProductMapper extends BaseMapper<Product> {

    @Select("SELECT pro.*,car.carid,car.userid,car.proid,car.checked,car.quantity " +
            "FROM cart car LEFT JOIN product pro " +
            "ON car.proid=pro.proid " +
            "WHERE car.userid=#{userid}")
    List<Map<String,Object>> getAllCartproduct(Page<Map<String,Object>> page, String userid);
}
