package io.atomiclimes.security.server;

import java.security.KeyPair;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

//@Configuration
//@EnableAuthorizationServer
//@EnableConfigurationProperties(ServerProperties.class)
public class AtomicLimesAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

	private BCryptPasswordEncoder passwordEncoder;

	private AtomicLimesAuthorisationServerProperties properties;

	private UserDetailsService userDetailsService;
	
	public AtomicLimesAuthorizationServerConfigurerAdapter(BCryptPasswordEncoder passwordEncoder, AtomicLimesAuthorisationServerProperties properties, UserDetailsService userDetailsService) {
		this.passwordEncoder = passwordEncoder;
		this.properties = properties;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("authserver").secret(passwordEncoder.encode("passwordforauthserver"))
				.authorizedGrantTypes("authorization_code").scopes("user_info").autoApprove(true)
				.redirectUris("http://localhost:8080/login");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(properties.getKeystore(),
				properties.getKeystorePassword().toCharArray());
		KeyPair keyPair = keyStoreKeyFactory.getKeyPair(properties.getKeyAlias(),
				properties.getKeyPassword().toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair);
		return converter;
	}
}
