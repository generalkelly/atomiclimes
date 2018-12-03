package smartmeter.web.gui;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
public class SmartMeterWebGuiConfiguration { // implements WebMvcConfigurer, ApplicationContextAware

//	private ApplicationContext applicationContext;
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//	}
//
//	@Bean
//	public ViewResolver htmlViewResolver() {
//		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
//		resolver.setContentType("text/html");
//		resolver.setCharacterEncoding("UTF-8");
//		resolver.setViewNames(ArrayUtils.toArray("*.html"));
//		return resolver;
//	}
//
//	@Bean
//	public ViewResolver javascriptViewResolver() {
//		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//		resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
//		resolver.setContentType("application/javascript");
//		resolver.setCharacterEncoding("UTF-8");
//		resolver.setViewNames(ArrayUtils.toArray("*.js"));
//		return resolver;
//	}
//
//	@Bean
//	public ViewResolver plainViewResolver() {
//		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//		resolver.setTemplateEngine(templateEngine(plainTemplateResolver()));
//		resolver.setContentType("text/plain");
//		resolver.setCharacterEncoding("UTF-8");
//		resolver.setViewNames(ArrayUtils.toArray("*.txt"));
//		return resolver;
//	}
//
//	private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
//		SpringTemplateEngine engine = new SpringTemplateEngine();
//		engine.setTemplateResolver(templateResolver);
//		return engine;
//	}
//
//	private ITemplateResolver htmlTemplateResolver() {
//		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//		resolver.setApplicationContext(applicationContext);
//		resolver.setPrefix("/WEB-INF/views/");
//		resolver.setCacheable(false);
//		resolver.setTemplateMode(TemplateMode.HTML);
//		return resolver;
//	}
//
//	private ITemplateResolver javascriptTemplateResolver() {
//		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//		resolver.setApplicationContext(applicationContext);
//		resolver.setPrefix("/WEB-INF/js/");
//		resolver.setCacheable(false);
//		resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
//		return resolver;
//	}
//
//	private ITemplateResolver plainTemplateResolver() {
//		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//		resolver.setApplicationContext(applicationContext);
//		resolver.setPrefix("/WEB-INF/txt/");
//		resolver.setCacheable(false);
//		resolver.setTemplateMode(TemplateMode.TEXT);
//		return resolver;
//	}

}
