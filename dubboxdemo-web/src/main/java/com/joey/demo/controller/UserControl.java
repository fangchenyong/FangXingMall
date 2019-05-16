package com.joey.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.joey.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈〉
 *
 * @author Joey
 * @create 2019-05-15
 * @since 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserControl {

    @Reference
    private UserService userService;

    @RequestMapping("/showName")
    @ResponseBody
    public String showName(){
        return userService.getName();
    }
}
