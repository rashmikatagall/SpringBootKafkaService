package com.rashmi.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaAdminConfig {

	private final KafkaConfigData kafkaConfigData;
	
	public KafkaAdminConfig(KafkaConfigData kafkaConfigData) {
		this.kafkaConfigData = kafkaConfigData;
	}
	
	@Bean
	public AdminClient adminClient() {
		
		Map<String,Object> properties = new HashMap<>();
		properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.getBootstrapServers());
		return AdminClient.create(properties);
			
	}
}
