package de.rub.iaw;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
@EnableWebSecurity
// Profile to choose for login method. Spring only supports one LdapTemplate and both AD & LDAP auth are using one. Thus choosing before application start
@Profile("ActiveDirectory")
public class ActiveDirectorySecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String BASE_DN_PARTITION = "DC=IAW,DC=UNI,DC=DE";
	public static final String DOMAIN_NAME = "";
	public static final String DOMAIN_CONTROLLER_URL = "ldap://abc:123";
	
	@Bean
	public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
		
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(DOMAIN_NAME, DOMAIN_CONTROLLER_URL);
		provider.setConvertSubErrorCodesToExceptions(true);
		provider.setUseAuthenticationRequestCredentials(true);
		
		return provider;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
         authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider()).userDetailsService(userDetailsService());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
    }
    
	@Bean
	@ConfigurationProperties(prefix = "datasource.secondary")
	public LdapContextSource ldapContextSource() {
		LdapContextSource contextSource = new LdapContextSource();

		// Currently works for simple authentication!
		contextSource.setBase(""); // needs to be empty somehow for AD ldap
		contextSource.setUrl(DOMAIN_CONTROLLER_URL);
		contextSource.setUserDn("CN=Student Student,OU=Studenten,OU=IMTM" + "," + BASE_DN_PARTITION);
		contextSource.setPassword("iawstudent");
		contextSource.afterPropertiesSet();

		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		LdapTemplate template = new LdapTemplate(ldapContextSource());
		template.setIgnorePartialResultException(true);
		return template;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
				// Currently disabled to test http put/post
			.authorizeRequests().anyRequest().authenticated()
				.and()
			.formLogin()
				// .loginPage("/login") // custom login page
				// .permitAll() // allow all users access to custom login page
				.and()
			.httpBasic()
				// used to test with postman (chrome extension)
				.and()
			.logout().invalidateHttpSession(true).logoutSuccessUrl("/");
	}
}
