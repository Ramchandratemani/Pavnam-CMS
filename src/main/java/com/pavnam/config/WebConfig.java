package com.pavnam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

	
	@Bean
	public UserDetailsService userDetailsService() {
			return new CustomUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(requests -> requests
				// Static resources should be fully accessible
				.requestMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/fonts/**", "/bootstrap/**", "/favicon.ico").permitAll()
				
				.requestMatchers("/error").permitAll()

				// Publicly accessible routes
				.requestMatchers("/favicon.ico", "/error", "/register", "/add", "/register/admin", "/add/admin", 
					"/register/superadmin", "/add/superadmin", "/forgot-password", "/reset-password").permitAll()
				.requestMatchers("/about", "/certification", "/contact", "/faq", "/pavnamcms", "/supporting", 
					"/vegrevolution", "/index", "/").permitAll()
				
				// Role-based access control
				.requestMatchers("/dealer/admin-dashboard", "/admin-dashboard", "/api/dealer/**", "/uploads/**", "/dealer/**", 
					"/dealer/certifiedUserDetails").hasAuthority("ADMIN")
				.requestMatchers("/superadmin/superadmin-dashboard", "/superadmin-dashboard", "/uploads/**", "/superadmin/**", 
					"/superadmin/profile").hasAuthority("SUPERADMIN")
				// .requestMatchers("/cmsindex").hasAnyAuthority("USERS", "ADMIN", "SUPERADMIN")
				.requestMatchers("/cmsindex").permitAll()
				
				// Secure all other requests
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.loginPage("/login")
				.defaultSuccessUrl("/cmsindex", true)
				.failureUrl("/login?error=true") // Redirects back to login on failure
				.permitAll()
			);
	
		return httpSecurity.build();
	}
	
	
}