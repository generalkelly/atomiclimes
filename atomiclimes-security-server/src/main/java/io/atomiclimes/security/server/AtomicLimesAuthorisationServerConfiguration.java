package io.atomiclimes.security.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Order(1)
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
@EnableAuthorizationServer
@EnableConfigurationProperties(AtomicLimesAuthorisationServerProperties.class)
public class AtomicLimesAuthorisationServerConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/token").and().authorizeRequests().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().and().csrf().disable();
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/css/**", "/img/**", "/js/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("mpohland").password(passwordEncoder().encode("mpohland"))
				.roles("USER", "ADMIN").and().withUser("sbiernacki").password(passwordEncoder().encode("sbiernacki"))
				.roles("USER", "ADMIN");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean(name = "userDetailsService")
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Override
	@Bean(name = "authenticationManager")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	AuthorizationServerConfigurerAdapter authorizationServerConfigurerAdapter(BCryptPasswordEncoder passwordEncoder,
			AtomicLimesAuthorisationServerProperties properties, UserDetailsService userDetailsService,
			AuthenticationManager authenticationManager) {
		return new AtomicLimesAuthorizationServerConfigurerAdapter(passwordEncoder, properties, userDetailsService,
				authenticationManager);
	}

	@Bean
	public SimpleUrlHandlerMapping customFaviconHandlerMapping() {
		SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setOrder(Integer.MIN_VALUE);
		mapping.setUrlMap(Collections.singletonMap("/favicon.ico", faviconRequestHandler()));
		return mapping;
	}

	@Bean
	protected ResourceHttpRequestHandler faviconRequestHandler() {
		ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
		ClassPathResource classPathResource = new ClassPathResource("static/img");
		List<Resource> locations = Arrays.asList(classPathResource);
		requestHandler.setLocations(locations);
		return requestHandler;
	}

}
