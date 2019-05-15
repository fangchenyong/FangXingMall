package com.joey.service;

import com.joey.bean.User;
import com.joey.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 〈〉
 *
 * @author Joey
 * @create 2019-05-15
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map getUserById(Integer id){
       return userMapper.getUserById(id);
    }
}
