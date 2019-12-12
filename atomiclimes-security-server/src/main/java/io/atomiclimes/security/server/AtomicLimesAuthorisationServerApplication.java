package io.atomiclimes.security.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@SpringBootApplication
@EnableResourceServer
@EnableAtomicLimesClient(type = ClientType.GUI)
public class AtomicLimesAuthorisationServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesAuthorisationServerApplication.class, args);
	}
}
