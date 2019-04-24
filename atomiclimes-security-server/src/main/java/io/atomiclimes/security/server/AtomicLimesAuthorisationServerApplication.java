package io.atomiclimes.security.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class AtomicLimesAuthorisationServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AtomicLimesAuthorisationServerApplication.class, args);
	}



//	@Configuration
//	@EnableAuthorizationServer
//	public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
//
//		@Autowired
//		private AuthenticationManager authenticationManager;
//
//		@Autowired
//		private UserDetailsService userDetailsService;
//
//		@Override
//		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//			security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//		}
//
//		@Override
//		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////			clients.inMemory().withClient("authserver").secret("{noop}passwordforauthserver")
////					.authorizedGrantTypes("refresh_token", "password", "client_credentials").scopes("user_info");
//			clients.inMemory().withClient("authserver").secret("{noop}passwordforauthserver")
//					.redirectUris("http://localhost:8080/").authorizedGrantTypes("authorization_code", "refresh_token")
//					.scopes("user_info").autoApprove(true).accessTokenValiditySeconds(30)
//					.refreshTokenValiditySeconds(1800);
//		}
//
//		@Override
//		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//			endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
//		}
//	}
}

//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//
//import com.baeldung.config.AuthServerConfigurer;
//
//@Configuration
//@Order(1)
//@EnableWebSecurity
//@EnableAuthorizationServer@EnableConfigurationProperties(ServerProperties.class)
//public class ServerConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
////		http.requestMatchers().antMatchers("/login", "/oauth/authorize").and().authorizeRequests().anyRequest()
////				.authenticated().and().formLogin().permitAll();
//
//		http.requestMatchers().antMatchers("/login", "/oauth/authorize").and().csrf().disable().authorizeRequests()
//				.anyRequest().authenticated().and().formLogin();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER").and().withUser("admin")
//				.password("{noop}admin").roles("USER", "ADMIN");
//	}
//
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Bean
//	public AuthorizationServerConfigurerAdapter authorizationServerConfigurerAdapter(AuthenticationManager authenticationManager) {
//		return new AuthServerConfigurer(authenticationManager);
//	}
//
//}
