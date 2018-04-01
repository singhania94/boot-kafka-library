package com.github.kafka;

import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {


	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	@Autowired RequestProducer<String> producer;
	@Value("${spring.kafka.producer.path.count:100}") int pathCount;
	@Value("${spring.kafka.producer.message.count:1000}") int messageCount;
	String simPath = "sample_sim_path_";
	String request = "sample_request_";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		boolean response = false;
		String k, v;

		do {
			for (int i=0; i<messageCount; i++) {
				k = simPath + r.nextInt(pathCount);
				v = request + i + "_for_" + k;
				producer.send(k , v);

			}
			LOGGER.info("Do you wish to insert more messages?(true/false)");
			response = sc.nextBoolean();
		} while(response);
		sc.close();
	}
}
