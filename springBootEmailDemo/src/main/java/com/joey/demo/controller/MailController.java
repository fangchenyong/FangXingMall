package com.joey.demo.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joey.demo.service.MailService;

@Controller
public class MailController {
    @Autowired
    private MailService mailService;
 
    @RequestMapping("getCheckCode")
    @ResponseBody
    public String getCheckCode(String email){
    	System.out.println(email);
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
        	System.out.println(e);
            return "";
        }
        System.out.println(message);
        return checkCode;
    }
}
