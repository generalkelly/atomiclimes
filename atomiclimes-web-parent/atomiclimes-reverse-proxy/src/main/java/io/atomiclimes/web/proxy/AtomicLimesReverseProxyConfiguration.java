package io.atomiclimes.web.proxy;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.ForwardedHeaderFilter;

import io.atomiclimes.client.notification.filter.ClientNotificationFilter;
import io.atomiclimes.data.service.master.events.ClientNotificationType;
import io.atomiclimes.web.proxy.routes.AtomicLimesZuulRoutesAdministration;

@EnableZuulProxy
@EnableOAuth2Sso
@Configuration
public class AtomicLimesReverseProxyConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login**").permitAll().antMatchers("/clientNotification/**").permitAll()
				.antMatchers("/**").authenticated().and().formLogin().disable().logout().logoutSuccessUrl("/")
				.permitAll().and().csrf().disable();

	}

	@Bean
	public ForwardedHeaderFilter forwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}

	@Bean
	ClientNotificationFilter clientRegisteredFilter(AtomicLimesZuulRoutesAdministration zuulRoutesAdministration) {
		return clientNotification -> {
			if (clientNotification.getClientNotificationType().equals(ClientNotificationType.CLIENT_REGISTERED)) {
				zuulRoutesAdministration.add(clientNotification.getClient());
			}
		};
	}

	@Bean
	ClientNotificationFilter clientDeregisteredFilter(AtomicLimesZuulRoutesAdministration zuulRoutesAdministration) {
		return clientNotification -> {
			if (clientNotification.getClientNotificationType().equals(ClientNotificationType.CLIENT_DEREGISTERED)) {
				zuulRoutesAdministration.remove(clientNotification.getClient());
			}
		};
	}

	@Bean
	AtomicLimesZuulRoutesAdministration zuulRoutesAdministration(ZuulProperties zuulProperties,
			ZuulHandlerMapping zuulHandlerMapping) {
		return new AtomicLimesZuulRoutesAdministration(zuulProperties, zuulHandlerMapping);
	}

}
