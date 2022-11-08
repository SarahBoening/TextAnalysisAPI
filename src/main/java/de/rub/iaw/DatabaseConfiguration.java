package de.rub.iaw;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.rub.iaw", entityManagerFactoryRef="contentdbEntityManagerFactory")
@Configuration
public class DatabaseConfiguration {

	private static Logger log = LogManager.getLogger(DatabaseConfiguration.class.getName());

	// might be interesting....
	// http://stackoverflow.com/questions/23850585/how-to-start-spring-boot-app-without-depending-on-database

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "contentdbTransactionManager")
	@Autowired
	public PlatformTransactionManager contentdbTransactionManager() throws SQLException {
		log.info("transactionManager");

		EntityManagerFactory factory = contentdbEntityManagerFactory().getObject();

		return new JpaTransactionManager(factory);
	}

	@Bean(name="contentdbEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean contentdbEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(Boolean.TRUE);
		vendorAdapter.setGenerateDdl(Boolean.TRUE);

		factory.setJpaProperties(content_hibernateProperties());
		factory.setDataSource(contentdbDataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("de.rub.iaw.domain");

		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

		return factory;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	@Primary
	// Needed to configure two data sources
	@ConfigurationProperties(prefix = "datasource.primary")
	public DataSource contentdbDataSource() {
		log.info("primaryDataSource");

		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		//for local development
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/rotterdam");
		dataSource.setUsername("employid");
		dataSource.setPassword("JupAWsdrx123");

		return dataSource;
	}

	@Bean
	public Properties content_hibernateProperties() {
		log.info("hibernateProperties");

		Properties properties = new Properties();

		properties.put("hibernate.format_sql", "true");
		// Enable hibernate to automatically set utf8 collation on table creation
		properties.put("hibernate.connection.CharSet", "utf8");
		properties.put("hibernate.connection.characterEncoding", "utf8");
		properties.put("hibernate.connection.useUnicode", "true");

		return properties;
	}
}