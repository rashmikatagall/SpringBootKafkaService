package com.rashmi.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rashmi.model.User;

@Service
public class KafkaProducerService {

	@Autowired
	KafkaTemplate<String,String> kafkaTemplate;
	
	private static final String TOPIC="first_topic";
	
	public User addUser(User user) {
	
	 kafkaTemplate.send(TOPIC, user.toString());
	 return user;
	}

	
	
}
