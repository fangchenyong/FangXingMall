package com.joey.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.joey.demo.TopicProducer;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-jms-producer.xml")
public class TestTopic {
	@Autowired
	private TopicProducer topicProducer;
	@Test
	public void sendTextQueue(){		
		topicProducer.sendTextMessage("发布订阅");
	}	
}
