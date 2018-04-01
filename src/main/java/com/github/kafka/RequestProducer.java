package com.github.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class RequestProducer<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProducer.class);
	ListenableFuture<SendResult<String, T>> result;
	@Value("${spring.kafka.producer.topic-prefix:ace_pricer}") String prefix;
	@Value("${spring.kafka.producer.topic-default:default_sim_path}") String defaultTopic;
	@Autowired private KafkaTemplate<String, T> kafkaTemplate;

	public void send(String key, T payload) {
		try {
			result = (key == null || key.equals("")) ?
					kafkaTemplate.send(defaultTopic, key, payload) :
					kafkaTemplate.send(prefix + "_" + key, key, payload);
			LOGGER.info("Key={};Payload={};Completed={}", key, payload, result.isDone());
		} catch (Exception ex) {
			LOGGER.error("An Exception has occured.", ex);
		}
	 }
}
