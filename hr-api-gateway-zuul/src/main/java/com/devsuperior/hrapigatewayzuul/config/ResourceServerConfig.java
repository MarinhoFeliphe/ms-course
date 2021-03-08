package com.devsuperior.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC_ROUTES_ALLOWED = { "/hr-oauth/oauth/token" };
	private static final String[] OPERATOR_ROUTES_ALLOWED = { "/hr-worker/**" };
	private static final String[] ADMIN_ROUTES_ALLOWED = { "/hr-payroll/**", "/hr-user/**", "/actuator/**", "/hr-worker/actuator/**", "/hr-oauth/actuator/**" };
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(this.tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(PUBLIC_ROUTES_ALLOWED).permitAll()
			.antMatchers(HttpMethod.GET, OPERATOR_ROUTES_ALLOWED).hasAnyRole("OPERATOR", "ADMIN")
			.antMatchers(ADMIN_ROUTES_ALLOWED).hasRole("ADMIN")
			.anyRequest().authenticated();
	}
	
	

}
