package com.authorize.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.authorize.service.implementation.CustomPermissionHandler;
import com.authorize.service.implementation.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration	
public class SecurityConfig {
	
	
	protected static final String[] PUBLIC_PATHS = {
            "/v3/api-docs.yaml",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/login",
            "/auth/token",
            "/auth/validate",
            "/users/**",
            "/api/roles/**"
    };
	
	@Bean
	public CustomPermissionHandler customPermissionHandler() {
		return new CustomPermissionHandler();
	}
	
	@Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionHandler());
        return expressionHandler;
    }
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("auth config");
        return httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(PUBLIC_PATHS).permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/orgs/**").hasAuthority("ORGANIZATION_READ_PRIVILEGE")
                    .requestMatchers(HttpMethod.POST,"/api/orgs").hasAuthority("ORGANIZATION_CREATE_PRIVILEGE")
                    .requestMatchers(HttpMethod.PUT,"/api/orgs").hasAuthority("ORGANIZATION_UPDATE_PRIVILEGE")
                    .requestMatchers(HttpMethod.DELETE,"/api/orgs").hasAuthority("ORGANIZATION_DELETE_PRIVILEGE")
                    .requestMatchers(HttpMethod.GET,"/api/fields").hasAuthority("FIELD_READ_PRIVILEGE")
                    .requestMatchers(HttpMethod.POST,"/api/fields").hasAuthority("FIELD_CREATE_PRIVILEGE")
                    .requestMatchers(HttpMethod.PUT,"/api/fields").hasAuthority("FIELD_UPDATE_PRIVILEGE")
                    .requestMatchers(HttpMethod.DELETE,"/api/fields").hasAuthority("FIELD_DELETE_PRIVILEGE")
                    .anyRequest()
                    .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
    	log.info("authentication provider");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	log.info("authentication configuration");
        return config.getAuthenticationManager();
    }
}
