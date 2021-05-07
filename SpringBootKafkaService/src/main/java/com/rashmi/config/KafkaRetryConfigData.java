package com.rashmi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "kafka-retry")
@Component
public class KafkaRetryConfigData {

	 private int maxRetryAttempts;
	 private int retryInterval;
	 private int retryMultiplier;
}
