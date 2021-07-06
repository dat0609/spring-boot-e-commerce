package com.shopme.site.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopme.site.security.oauth.CustomerOauth2UserService;
import com.shopme.site.security.oauth.Oauth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	CustomerOauth2UserService customerOauth2UserService;
	
	@Autowired
	Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailService();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService());	
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/account_detail","/product/**","/cart").authenticated()
			.anyRequest().permitAll()
			.and()
				.formLogin().loginPage("/login").usernameParameter("email").defaultSuccessUrl("/").permitAll()
			.and()
				.oauth2Login().loginPage("/login").userInfoEndpoint().userService(customerOauth2UserService)
			.and()
				.successHandler(oauth2LoginSuccessHandler)
			.and()
				.logout().permitAll();
	}
	
}
