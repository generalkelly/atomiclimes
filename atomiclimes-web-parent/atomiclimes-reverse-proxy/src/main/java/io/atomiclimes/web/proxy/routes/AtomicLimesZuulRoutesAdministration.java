package io.atomiclimes.web.proxy.routes;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import io.atomiclimes.data.service.dto.AtomicLimesClient;

public class AtomicLimesZuulRoutesAdministration {

	private ZuulProperties zuulProperties;

	public AtomicLimesZuulRoutesAdministration(ZuulProperties zuulProperties) {
		this.zuulProperties = zuulProperties;
	}

	public void add(AtomicLimesClient client) {
		List<String> paths = client.getPaths();
		paths.stream().forEach(path -> {
			ZuulRoute zuulRoute = new ZuulRoute();
			StringBuilder pathBuilder = new StringBuilder();
			pathBuilder.append('/').append(path).append('/').append("**");
			zuulRoute.setPath(pathBuilder.toString());
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://").append(client.getIp()).append(':').append(client.getPort()).append('/')
					.append("path");
			zuulRoute.setUrl(urlBuilder.toString());
			zuulProperties.getRoutes().put(path, zuulRoute);
		});
	}

	public void remove(AtomicLimesClient client) {
		List<String> paths = client.getPaths();
		paths.stream().forEach(path -> {
			zuulProperties.getRoutes().remove(path);
		});
	}

}
