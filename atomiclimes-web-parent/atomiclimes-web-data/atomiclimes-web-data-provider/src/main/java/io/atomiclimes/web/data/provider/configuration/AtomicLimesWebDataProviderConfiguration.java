package io.atomiclimes.web.data.provider.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
@EnableWebMvc
@EnableConfigurationProperties(AtomicLimesWebDataProviderProperties.class)
public class AtomicLimesWebDataProviderConfiguration extends WebMvcConfigurationSupport {

	private AtomicLimesWebDataProviderProperties properties;

	public AtomicLimesWebDataProviderConfiguration(AtomicLimesWebDataProviderProperties properties) {
		this.properties = properties;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}