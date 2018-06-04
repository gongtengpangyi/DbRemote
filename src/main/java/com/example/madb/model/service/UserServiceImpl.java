package com.example.madb.model.service;

import com.example.madb.model.mapper.UserMapper;
import com.example.madb.model.pojo.User;
import com.frz.frame.ssm.BaseMapper;
import com.frz.frame.ssm.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public BaseMapper<User, Integer> getMapper() {
        return userMapper;
    }
}
