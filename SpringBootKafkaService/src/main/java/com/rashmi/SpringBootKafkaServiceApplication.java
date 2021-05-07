package com.rashmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rashmi.init.KafkaInitializer;

@SpringBootApplication
public class SpringBootKafkaServiceApplication implements CommandLineRunner {

	@Autowired
	private KafkaInitializer kafkaInitializer;
	
	private static final Logger LOG = LoggerFactory.getLogger(SpringBootKafkaServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		kafkaInitializer.init();
		LOG.info("Kafka topics created and ready for application");
		
	}

}
