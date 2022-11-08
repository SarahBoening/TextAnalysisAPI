//package de.rub.iaw;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Description;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.thymeleaf.spring4.SpringTemplateEngine;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
//
//@Configuration
//public class ThymeleafConfiguration extends WebMvcConfigurerAdapter {
//
//	private static Logger log = LogManager.getLogger(ThymeleafConfiguration.class.getName());
//	
//	@Bean
//    @Description("Thymeleaf template resolver serving HTML 5")
//    public ServletContextTemplateResolver templateResolver() {
//		log.info("ServletContextTemplateResolver");
//		
//        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
//        templateResolver.setPrefix("/WEB-INF/html/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        
//        return templateResolver;
//    }
//    
//    @Bean
//    @Description("Thymeleaf template engine with Spring integration")
//    public SpringTemplateEngine templateEngine() {
//    	log.info("SpringTemplateEngine");
//    	    	
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        
//        return templateEngine;
//    }
//    
//    @Bean
//    @Description("Thymeleaf view resolver")
//    public ThymeleafViewResolver viewResolver() {
//    	log.info("ThymeleafViewResolver");
//    	
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        
//        return viewResolver;
//    }
//    
//	
////    @Bean
////    @Description("Spring message resolver")
////    public ResourceBundleMessageSource messageSource() {  
////        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  
////        messageSource.setBasename("i18n/messages");  
////        
////        return messageSource;  
////    }
//}
