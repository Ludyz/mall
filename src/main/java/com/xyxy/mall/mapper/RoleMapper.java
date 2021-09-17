package com.xyxy.mall.mapper;

import com.xyxy.mall.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-09-16
 */
public interface RoleMapper extends BaseMapper<Role> {


    public Set<Role> getRolesByUserid(String userid);

}
