package io.atomiclimes.web.gui;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "io.atomiclimes.web.gui")
public class AtomicLimesWebProductionGuiProperties {

	private Resource keystore;

	private String keystorePassword;

	private String keyAlias;

	private String keyPassword;

	public Resource getKeystore() {
		return keystore;
	}

	public void setKeystore(Resource keystore) {
		this.keystore = keystore;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	
}
