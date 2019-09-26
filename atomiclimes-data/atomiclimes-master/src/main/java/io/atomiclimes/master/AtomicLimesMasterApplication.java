package io.atomiclimes.master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.atomiclimes.data.service.annotations.EnableAtomicLimesMaster;

@EnableAtomicLimesMaster
@SpringBootApplication
public class AtomicLimesMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesMasterApplication.class, args);
	}

}
