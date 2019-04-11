package smartmeter.web.gui;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartMeterWebGuiConfiguration {

	@Bean
	public WebApplication smartmeterWebApplication(ApplicationContext applicationContext) {
		return new SmartmeterWebApplication(applicationContext);
	}
	
	@Bean ServletContextInitializer servletContextInitializer() {
		return new SmartmeterServletContextInitializer();
	}

}
