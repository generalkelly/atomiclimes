package io.atomiclimes.web.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class AtomicLimesWebProductionGuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesWebProductionGuiApplication.class, args);
	}


}
