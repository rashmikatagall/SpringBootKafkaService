package com.rashmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rashmi.model.User;
import com.rashmi.producer.KafkaProducerService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	KafkaProducerService kafkaService;
	
	@PostMapping("/user")
	public ResponseEntity<User> addUser(@RequestBody User user)
	{
		return new ResponseEntity<User>(kafkaService.addUser(user),HttpStatus.OK);
	}
	

}
