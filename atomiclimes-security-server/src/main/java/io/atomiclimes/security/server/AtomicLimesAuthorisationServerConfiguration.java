package io.atomiclimes.security.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

@Order(1)
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
@EnableAuthorizationServer
@EnableConfigurationProperties(AtomicLimesAuthorisationServerProperties.class)
public class AtomicLimesAuthorisationServerConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/login", "/oauth/authorize").and().authorizeRequests().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().and().csrf().disable();
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/html/**");
		web.ignoring().antMatchers("/resources/**", "/css/**", "/img/**");

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

	@Bean
	AuthorizationServerConfigurerAdapter authorizationServerConfigurerAdapter(BCryptPasswordEncoder passwordEncoder,
			AtomicLimesAuthorisationServerProperties properties, UserDetailsService userDetailsService) {
		return new AtomicLimesAuthorizationServerConfigurerAdapter(passwordEncoder, properties, userDetailsService);
	}

}
