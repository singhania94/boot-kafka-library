package com.github.kafka;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseListener<K, V> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseListener.class);
	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(topics = "${spring.kafka.consumer.topic:ace_pricer_response}")
	public void receive(ConsumerRecord<K, V> consumerRecord) {
		LOGGER.info("Key={};Value={};", consumerRecord.key(), consumerRecord.value());
		latch.countDown();
	}
}
