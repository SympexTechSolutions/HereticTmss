package com.Travelamatemngementsystem.Tmss.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll()
	                .requestMatchers("/admin/**").hasRole("ADMIN")
	                .requestMatchers("/employee/**").hasRole("EMPLOYEE")
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	                .loginPage("/login")
	                .defaultSuccessUrl("/dashboard", true)
	                .permitAll()
	            )
	            .logout(logout -> logout.permitAll());

	        return http.build();
	    }

	    @Bean
	    public UserDetailsService userDetailsService() {
	        UserDetails admin = User.withDefaultPasswordEncoder()
	                .username("admin")
	                .password("admin123")
	                .roles("ADMIN")
	                .build();

	        UserDetails employee = User.withDefaultPasswordEncoder()
	                .username("employee")
	                .password("employee123")
	                .roles("EMPLOYEE")
	                .build();

	        return new InMemoryUserDetailsManager(admin, employee);
	    }
	} 