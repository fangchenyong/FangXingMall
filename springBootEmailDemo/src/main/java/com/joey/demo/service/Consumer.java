package com.joey.demo.service;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	@JmsListener(destination="joey")
	public void readMessage(String text){
		System.out.println("接收到消息："+text);
	}	
	
	@JmsListener(destination="joey_map")
	public void readMap(Map map){
		System.out.println(map);		
	}

}
