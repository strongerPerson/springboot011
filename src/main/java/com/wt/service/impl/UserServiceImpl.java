package com.wt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wt.mapper.UserMapper;
import com.wt.pojo.User;
import com.wt.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
