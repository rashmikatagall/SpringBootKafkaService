package com.rashmi.config;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rashmi.exception.KafkaServiceException;

@Component
public class KafkaAdminClient {

	private final static Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);
	private KafkaConfigData kafkaConfigData;
	private AdminClient adminClient;
	private KafkaRetryConfigData kafkaRetryConfigData;

	public KafkaAdminClient(KafkaConfigData kafkaConfigData, AdminClient adminClient,
			KafkaRetryConfigData kafkaRetryConfigData) {

		this.kafkaConfigData = kafkaConfigData;
		this.adminClient = adminClient;
		this.kafkaRetryConfigData = kafkaRetryConfigData;
	}

	public void createTopics() {
		if(areTopicsCreated())
		{
			LOG.info("All the Kafka Topics already exist");
			return;
		}
		CreateTopicsResult createTopicsResult = doCreateTopics();
		waitForTopicCreation();

	}

	private void waitForTopicCreation() {
		int retryAttempts = 1;
		int maxRetryAttempts = kafkaRetryConfigData.getMaxRetryAttempts();
		int retryMultiplier = kafkaRetryConfigData.getRetryMultiplier();
		int retryInterval = kafkaRetryConfigData.getRetryInterval();
		if(!areTopicsCreated())
		{
			checkRetryComplete(retryAttempts,maxRetryAttempts);
			sleep(retryInterval);
			retryInterval *= retryMultiplier;
			retryAttempts++;
		}
	}

	private void sleep(int retryInterval) {
		
		try {
			Thread.sleep(retryInterval);
		} catch (InterruptedException e) {
			throw new KafkaServiceException("Interrupted while waiting in retry for topic creation", e.getCause());
		}
		
	}

	private void checkRetryComplete(int retryAttempts, int maxRetryAttempts) {
		
		if(retryAttempts > maxRetryAttempts)
			throw new KafkaServiceException("Retry attempts for topic creation exceeded desired number of attempts!");
		
	}

	private boolean areTopicsCreated() {

		Collection<TopicListing> topicsCreated;
		try {
			topicsCreated = adminClient.listTopics().listings().get();
		} catch (InterruptedException | ExecutionException e) {
			throw new KafkaServiceException("Error listing topics created", e.getCause());
		}

		List<String> topicNamesCreated = topicsCreated.stream().map(t -> t.name()).collect(Collectors.toList());
		return topicNamesCreated.containsAll(kafkaConfigData.getTopicNamesToCreate());

	}

	private CreateTopicsResult doCreateTopics() {

		Collection<NewTopic> kafkaTopics = kafkaConfigData.getTopicNamesToCreate().stream()
				.map(t -> new NewTopic(t, kafkaConfigData.getNumOfPartitions(), kafkaConfigData.getReplicationFactor()))
				.collect(Collectors.toList());
		return adminClient.createTopics(kafkaTopics);

	}
}
