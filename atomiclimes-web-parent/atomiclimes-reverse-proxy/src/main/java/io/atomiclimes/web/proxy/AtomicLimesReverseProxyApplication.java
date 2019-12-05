package io.atomiclimes.web.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import io.atomiclimes.client.annotations.EnableAtomicLimesClient;
import io.atomiclimes.date.service.client.enums.ClientType;

@SpringBootApplication
@EnableZuulProxy
@EnableAtomicLimesClient(type = ClientType.PROXY)
public class AtomicLimesReverseProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesReverseProxyApplication.class, args);
	}

}
