package io.atomiclimes.web.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@SpringBootApplication
@EnableAtomicLimesClient(type = ClientType.GUI)
public class AtomicLimesWebProductionGuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesWebProductionGuiApplication.class, args);
	}

}
