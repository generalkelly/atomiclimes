package io.atomiclimes.web.gui.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import io.atomiclimes.web.gui.wicket.AtomicLimesWebApplication;

@Configuration
@EnableOAuth2Sso
@EnableWebMvc
public class AtomicLimesWebGuiConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/login**").permitAll().anyRequest().authenticated()
				.and().formLogin().disable().logout().logoutSuccessUrl("/").permitAll().and().csrf().disable();
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**");
	}

//	@Bean
//	public WebApplication smartmeterWebApplication(ApplicationContext applicationContext) {
//		return new AtomicLimesWebApplication(applicationContext);
//	}
//
//	@Bean
//	ServletContextInitializer servletContextInitializer() {
//		return new SmartmeterServletContextInitializer();
//	}

//	@Bean
//	public SimpleUrlHandlerMapping customFaviconHandlerMapping() {
//		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
//		mapping.setOrder(Integer.MIN_VALUE);
//		mapping.setUrlMap(Collections.singletonMap("/favicon.ico", faviconRequestHandler()));
//		return mapping;
//	}

//	@Bean
//	protected ResourceHttpRequestHandler faviconRequestHandler() {
//		ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
//		ClassPathResource classPathResource = new ClassPathResource("static/img");
//		List<Resource> locations = Arrays.asList(classPathResource);
//		requestHandler.setLocations(locations);
//		return requestHandler;
//	}

	@Bean
	protected WebMvcConfigurer webMvcConfigurer() {
		return new AtomicLimesWebMvcConfigurer();
	}

}
