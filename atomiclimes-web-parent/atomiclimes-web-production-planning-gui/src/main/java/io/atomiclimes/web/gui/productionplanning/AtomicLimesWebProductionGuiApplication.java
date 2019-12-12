package io.atomiclimes.web.gui.productionplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@SpringBootApplication
@EnableResourceServer
@EnableAtomicLimesClient(type = ClientType.GUI)
public class AtomicLimesWebProductionGuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesWebProductionGuiApplication.class, args);
	}

}