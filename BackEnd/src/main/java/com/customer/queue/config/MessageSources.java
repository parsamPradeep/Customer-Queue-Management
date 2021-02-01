package com.customer.queue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MessageSources {
	 @Bean
	    public ResourceBundleMessageSource errorCodeSource() {
	        ResourceBundleMessageSource errorCodeSource = new ResourceBundleMessageSource();
	        errorCodeSource.setBasenames("messageSource/errorCodes");
	        errorCodeSource.setUseCodeAsDefaultMessage(true);
	        return errorCodeSource;
	    }
	    
	    @Bean
	    public ResourceBundleMessageSource successCodeSource() {
	        ResourceBundleMessageSource successCodeSource = new ResourceBundleMessageSource();
	        successCodeSource.setBasenames("messageSource/successCodes");
	        successCodeSource.setUseCodeAsDefaultMessage(true);
	        return successCodeSource;
	    }
	    
	/*
	 * @Bean public CorsFilter corsFilter() { final UrlBasedCorsConfigurationSource
	 * source = new UrlBasedCorsConfigurationSource(); final CorsConfiguration
	 * config = new CorsConfiguration(); config.setAllowCredentials(true);
	 * 
	 * config.addAllowedOrigin("*"); config.addAllowedHeader("*");
	 * config.addAllowedMethod("*"); config.addExposedHeader("Authorization, " +
	 * "x-auth-token, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
	 * + "Content-Type, Access-Control-Request-Method");
	 * source.registerCorsConfiguration("/**", config); return new
	 * CorsFilter(source); }
	 */
}
