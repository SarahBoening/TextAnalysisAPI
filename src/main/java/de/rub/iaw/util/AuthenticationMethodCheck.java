package de.rub.iaw.util;

import java.util.Properties;

import de.rub.iaw.Application.LoginMethod;
import de.rub.iaw.PropertiesConfiguration;

/**
 * This class provides methods to check whether the application is configured for LDAP or AD authentication
 * @author blunk
 *
 */
public class AuthenticationMethodCheck {
	
	/**
	 * This methods determines whether the application is configured for Active Directory Authentication
	 * @return true if yes
	 */
	public static boolean isActiveDirectory() {
		Properties properties = PropertiesConfiguration.PropertyLoadHelper.loadProperties("application.properties");
		
		if(LoginMethod.ActiveDirectory.equals(LoginMethod.valueOf(properties.getProperty("spring.profiles.active")))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This methods determines whether the application is configured for Active Directory Authentication
	 * @return true if yes
	 */
	public static boolean isLDAP() {
		Properties properties = PropertiesConfiguration.PropertyLoadHelper.loadProperties("application.properties");
		
		if(LoginMethod.LDAP.equals(LoginMethod.valueOf(properties.getProperty("spring.profiles.active")))) {
			return true;
		}
		
		return false;
	}
	
}
