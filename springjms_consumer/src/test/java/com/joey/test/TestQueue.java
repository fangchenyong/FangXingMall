package com.joey.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-jms-queueconsumer.xml")
public class TestQueue {
	@Test
	public void testQueue(){
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
