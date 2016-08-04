package org.mdacc.rists.ristore.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@PropertySource(value = { "classpath:/config/application.properties" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

			.httpBasic()
			.and()
//			.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(
					SessionCreationPolicy.STATELESS)
			.and()
			.exceptionHandling()
	        .authenticationEntryPoint(restAuthenticationEntryPoint)
	        .and()
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/ristore/**").authenticated()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler());
//				.loginPage("/login");
	}
	
	@Override  
	@Bean  
	public AuthenticationManager authenticationManagerBean() throws Exception {  
		return super.authenticationManagerBean();  
	}

	@Bean
    public RestAuthenticationSuccessHandler mySuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
//	@Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
	
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
