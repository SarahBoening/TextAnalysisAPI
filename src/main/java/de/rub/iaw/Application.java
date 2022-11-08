package de.rub.iaw;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAutoConfiguration
@EnableWebMvc
@Configuration
@ComponentScan
public class Application extends SpringBootServletInitializer {

	// Enum of currently support login methods!
	public enum LoginMethod { ActiveDirectory, LDAP }
	
	private static Logger log = LogManager.getLogger(Application.class.getName());
	private static Class<Application> applicationClass = Application.class;

	public static void main(String[] args) {
		// http://stackoverflow.com/questions/11639997/how-do-you-configure-logging-in-hibernate-4
		System.setProperty("org.jboss.logging.provider", "log4j2");
		
		SpringApplication.run(applicationClass, args);
	}

	// Needed to start application on external application server (instead of the embedded one)
	// http://spring.io/blog/2014/03/07/deploying-spring-boot-applications
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
	
	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				System.out.println("ServletContext initialized");
				log.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				System.out.println("ServletContext destroyed");
			}

		};
	}

}
