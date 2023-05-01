package com.itheima.user.service.impl;

import com.itheima.user.entity.User;
import com.itheima.user.mapper.UserMapper;
import com.itheima.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itheima
 * @since 2020-03-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
