package com.joey.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.joey.demo.service.UserService;

/**
 * 〈〉
 *
 * @author Joey
 * @create 2019-05-15
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getName(){
        return "joey";
    }

}
