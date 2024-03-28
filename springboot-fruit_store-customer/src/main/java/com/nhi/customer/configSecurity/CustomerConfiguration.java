package com.nhi.customer.configSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nhi.customer.configSecurity.CustomerServiceConfig;

@EnableWebSecurity
@Configuration
public class CustomerConfiguration {
	
	@Autowired
	private CustomerServiceConfig customerServiceConfig;

	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.authorizeHttpRequests(request -> request
			.requestMatchers("/resource/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/webfonts/**").permitAll()
			.requestMatchers("/menu").permitAll()
			.requestMatchers("/products", "/products/byCategory", "/products/high-to-low-price",
					"/products/low-to-high-price", "/products/bestSeller").permitAll()
			.requestMatchers("/find-productById").permitAll()
			.requestMatchers("/login").permitAll()
			.requestMatchers("/register", "register-new").permitAll()
			.requestMatchers("/checkAuthentication").permitAll()
			.requestMatchers("/customer/**").hasAnyAuthority("CUSTOMER")
			.anyRequest().authenticated()
		)
		.formLogin(form -> form
			.loginPage("/login")
			.loginProcessingUrl("/do-login")
			.defaultSuccessUrl("/home", true)
			.permitAll()
		)
		.logout(logout -> logout 
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		)
		.headers(headers -> headers.frameOptions().sameOrigin());
		
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.customerServiceConfig);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}
}
