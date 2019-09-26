package io.atomiclimes.web.data.provider.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.atomiclimes.web.data.provider.configuration.AtomicLimesWebDataProviderProperties;
import io.atomiclimes.web.data.provider.connections.AtomicLimesInfluxDBConnector;
import io.atomiclimes.web.data.provider.connections.AtomicLimesRestTransporter;

@RestController
public class AtomicLimesWebDataProviderController {

	@Autowired
	private AtomicLimesWebDataProviderProperties properties;

	private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

	public AtomicLimesWebDataProviderController(AtomicLimesWebDataProviderProperties properties) {
		this.properties = properties;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/data")
	public SseEmitter getDataStream(@RequestParam(name = "agent") String agent,
			@RequestParam(name = "measurement") String measurement,
			@RequestParam(name = "time-window", required = false) String timeWindow) {
		SseEmitter emitter = new SseEmitter();
		AtomicLimesInfluxDBConnector atomicLimesInfluxDBConnector = new AtomicLimesInfluxDBConnector("atomiclimes");
		atomicLimesInfluxDBConnector.configure(properties.getInfluxDbConfig());

		AtomicLimesRestTransporter transporter = new AtomicLimesRestTransporter(atomicLimesInfluxDBConnector, emitter,
				agent, measurement, timeWindow);
		nonBlockingService.execute(transporter);
		return emitter;
	}

}
