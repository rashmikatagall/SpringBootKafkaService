package com.rashmi.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "kafka-configuration")
@Component
public class KafkaConfigData {
	
	private String bootstrapServers;
    private String topicName;
    private List<String> topicNamesToCreate; 
    private int numOfPartitions;
    private short replicationFactor;   

}
