package io.atomiclimes.web.data.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;

@EnableAtomicLimesClient
@SpringBootApplication
public class AtomicLimesWebDataProvider {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesWebDataProvider.class, args);
	}

}
