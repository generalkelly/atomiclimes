package smartmeter.web.gui;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class SmartMeterWebGuiConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/login**").permitAll().anyRequest().authenticated()
				.and().formLogin().disable();
	}

	@Bean
	public PrincipalExtractor baeldungPrincipalExtractor() {
		return new BaeldungPrincipalExtractor();
	}

	@Bean
	public AuthoritiesExtractor baeldungAuthoritiesExtractor() {
		return new BaeldungAuthoritiesExtractor();
	}

	@Bean
	public WebApplication smartmeterWebApplication(ApplicationContext applicationContext) {
		return new SmartmeterWebApplication(applicationContext);
	}

	@Bean
	ServletContextInitializer servletContextInitializer() {
		return new SmartmeterServletContextInitializer();
	}

}
