package de.rub.iaw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

@Configuration
@EnableWebSecurity
@Profile("LDAP")
// attribute needed to route requests to ldap database
// @EnableLdapRepositories(basePackages={"de.rub.iaw.domain.ldap"})
public class LdapSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// http://stackoverflow.com/questions/22633668/spring-security-with-java-config-doesn%C2%B4t-work-erasecredentials-method

	public static final String BASE_DN_PARTITION = "dc=springframework,dc=org";

	@Bean
	@ConfigurationProperties(prefix = "datasource.secondary")
	public LdapContextSource ldapContextSource() {
		LdapContextSource contextSource = new LdapContextSource();

		contextSource.setBase(BASE_DN_PARTITION);
		//local development
		contextSource.setUrl("ldap://127.0.0.1:10389/");

		contextSource.setUserDn("uid=admin,ou=system");
		contextSource.setPassword("JupAWsdrx123");

		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(ldapContextSource());
	}

	@Bean
	public FilterBasedLdapUserSearch filterBasedLdapUserSearch() {
		return new FilterBasedLdapUserSearch("ou=people", "uid={0}",
				this.ldapContextSource());
	}

	@Bean
	public LdapAuthenticator ldapAuthenticator() {
		BindAuthenticator authenticator = new BindAuthenticator(this.ldapContextSource());
		authenticator.setUserSearch(this.filterBasedLdapUserSearch());
		return authenticator;
	}

	@Bean
	public LdapAuthenticationProvider ldapAuthenticationProvider() {
		return new LdapAuthenticationProvider(this.ldapAuthenticator());
	}

	@Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		//auth.eraseCredentials(false).authenticationProvider(this.ldapAuthenticationProvider());
		 auth.inMemoryAuthentication()
		 .withUser("user").password("pw").roles("USER").and()
		 .withUser("admin").password("admin").roles("USER", "ADMIN");
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
