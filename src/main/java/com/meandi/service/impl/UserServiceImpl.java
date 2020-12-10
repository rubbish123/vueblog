package com.meandi.service.impl;

import com.meandi.pojo.User;
import com.meandi.mapper.UserMapper;
import com.meandi.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Meandi
 * @since 2020-12-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
