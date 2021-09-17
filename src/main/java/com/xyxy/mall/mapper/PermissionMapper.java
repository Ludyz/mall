package com.xyxy.mall.mapper;

import com.xyxy.mall.pojo.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    public Set<Permission> getPermissionByRoleIdSet(Set<Integer> roleIdSet );
}
