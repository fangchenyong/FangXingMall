package com.joey.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
    @Value("${spring.mail.username}")
    private String from;
	
    @Autowired
    private JavaMailSender mailSender;
	@JmsListener(destination="joey")
	public void readMessage(String text){
		System.out.println("接收到消息："+text);
	}	
	
	@JmsListener(destination="email")
	public void readMap(Map map){
		System.out.println(map);		
		
		System.out.println(map.get("email"));
    	System.out.println(map.get("checkCode"));
       // String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //String message = "您的注册验证码为："+map.get("checkCode");
        try {
            //mailService.sendSimpleMail(map.get("email"), "注册验证码", message);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(map.get("email").toString());
            message.setSubject("方兴商城注册邮件");
            message.setText("您的注册验证码为："+map.get("checkCode"));
            mailSender.send(message);
            System.out.println("成功");
        }catch (Exception e){
        	System.out.println(e);
        }
	}

}
