package com.rashmi.init;

import org.springframework.stereotype.Component;

import com.rashmi.config.KafkaAdminClient;

@Component
public class KafkaInitializerImpl implements KafkaInitializer {

	private KafkaAdminClient kafkaAdminClient;
	
	public KafkaInitializerImpl(KafkaAdminClient kafkaAdminClient) {
		
		this.kafkaAdminClient = kafkaAdminClient;
	}
	
	@Override
	public void init() {
		kafkaAdminClient.createTopics();
	}

}
