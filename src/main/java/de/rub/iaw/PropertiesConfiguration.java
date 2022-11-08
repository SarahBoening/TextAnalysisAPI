package de.rub.iaw;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return PropertyLoadHelper.getPropertySourcesPlaceholderConfigurer();
	}

	public static class PropertyLoadHelper {
		public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
			PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
			properties.setLocations(new ClassPathResource[] {
					new ClassPathResource("application.properties") });
			properties.setBeanName("app");
			properties.setLocalOverride(true);
			properties.setIgnoreResourceNotFound(true);
			return properties;
		}

		public static Properties loadProperties(String propertiesPath) {
			PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
			propertiesFactoryBean.setLocation(new ClassPathResource(
					propertiesPath));
			Properties properties = null;
			try {
				propertiesFactoryBean.afterPropertiesSet();
				properties = propertiesFactoryBean.getObject();

			} catch (IOException e) {
				e.printStackTrace();
			}
			return properties;
		}
	}
}