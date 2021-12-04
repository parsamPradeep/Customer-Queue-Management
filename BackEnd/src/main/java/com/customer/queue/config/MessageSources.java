package com.customer.queue.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

	 @Bean
	    public CorsFilter corsFilter() {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOriginPatterns(Arrays.asList("*"));
	        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", "Authorization"));
	        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }      

}
