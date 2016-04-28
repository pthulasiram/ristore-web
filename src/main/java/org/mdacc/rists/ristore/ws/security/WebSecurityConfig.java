package org.mdacc.rists.ristore.ws.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebSecurity
@PropertySource(value = { "classpath:/config/application.properties" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
			.formLogin();
			
	}
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
//	@Value("${ldap.contextSource.url:ldap://ldap.mdanderson.edu:389/dc=mdanderson,dc=edu}")
//	private  String url;
	
//	@Value("${ldap.contextSource.managerDn}")
//	private static String userDn;
//	
//	@Value("${ldap.contextSource.managerPass}")
//	private static String userPass;
	
	@Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {
		
		@Override
        public void init(AuthenticationManagerBuilder auth) throws Exception { 
//			DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(url);
//            contextSource.setUserDn(userDn);
//            contextSource.setPassword(userPass);
			DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://ldap.mdanderson.edu:389/dc=mdanderson,dc=edu");
            contextSource.setUserDn("cn=ris_flow,ou=service accounts,ou=institution,ou=service accounts,dc=mdanderson,dc=edu");
            contextSource.setPassword("!BMpl@tform2O15");
            contextSource.setReferral("follow"); 
            contextSource.afterPropertiesSet();

            LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = auth.ldapAuthentication();

            ldapAuthenticationProviderConfigurer
            	.userDnPatterns("cn={0},ou=institution,ou=people")
                .userSearchBase("")
                .contextSource(contextSource);
        }
    }

}
