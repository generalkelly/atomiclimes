package io.atomiclimes.web.proxy.routes;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import io.atomiclimes.common.logging.AtomicLimesLogger;
import io.atomiclimes.data.service.dto.AtomicLimesClient;
import io.atomiclimes.web.proxy.logging.AtomicLimesReverseProxyLogMessage;

public class AtomicLimesZuulRoutesAdministration {

	private ZuulProperties zuulProperties;

	private ZuulHandlerMapping zuulHandlerMapping;

	private static final AtomicLimesLogger LOG = new AtomicLimesLogger(AtomicLimesZuulRoutesAdministration.class);

	public AtomicLimesZuulRoutesAdministration(ZuulProperties zuulProperties, ZuulHandlerMapping zuulHandlerMapping) {
		this.zuulProperties = zuulProperties;
		this.zuulHandlerMapping = zuulHandlerMapping;
	}

	public void add(AtomicLimesClient client) {
		List<String> paths = client.getPaths();
		paths.stream().forEach(path -> {
			ZuulRoute zuulRoute = new ZuulRoute();
			StringBuilder pathBuilder = new StringBuilder();
			pathBuilder.append('/').append(path).append('/').append("**");
			zuulRoute.setPath(pathBuilder.toString());
			zuulRoute.setId(pathBuilder.toString());
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("http://").append(client.getIp()).append(':').append(client.getPort()).append('/')
					.append(path);
			zuulRoute.setUrl(urlBuilder.toString());
			zuulProperties.getRoutes().put("/" + client.getName() + pathBuilder.toString(), zuulRoute);
			zuulHandlerMapping.setDirty(true);
			LOG.info(AtomicLimesReverseProxyLogMessage.ADDED_ROUTE_TO_REVERSE_PROXY, zuulRoute.getUrl());

		});
	}

	public void remove(AtomicLimesClient client) {
		List<String> paths = client.getPaths();
		paths.stream().forEach(path -> {
			LOG.info(AtomicLimesReverseProxyLogMessage.REMOVED_PATH_FROM_REVERSE_PROXY, path);
			zuulProperties.getRoutes().remove(path);
			zuulHandlerMapping.setDirty(true);
		});
	}

}
