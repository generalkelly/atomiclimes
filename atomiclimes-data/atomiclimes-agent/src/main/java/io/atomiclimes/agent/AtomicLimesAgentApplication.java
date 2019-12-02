package io.atomiclimes.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

/**
 * @author Mirko Pohland
 *
 */
@SpringBootApplication
@EnableAtomicLimesClient(type = ClientType.AGENT)
public class AtomicLimesAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesAgentApplication.class, args);
	}

}
