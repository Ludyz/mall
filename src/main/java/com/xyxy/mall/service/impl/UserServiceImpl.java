package com.xyxy.mall.service.impl;

import com.xyxy.mall.pojo.User;
import com.xyxy.mall.mapper.UserMapper;
import com.xyxy.mall.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
