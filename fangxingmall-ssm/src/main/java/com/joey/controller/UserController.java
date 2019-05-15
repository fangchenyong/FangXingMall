package com.joey.controller;

import com.joey.bean.User;
import com.joey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 〈〉
 *
 * @author Joey
 * @create 2019-05-15
 * @since 1.0.0
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public Map getUserById(){
        int id = 1;
        Map map =  userService.getUserById(id);
        return map;
    }
}
