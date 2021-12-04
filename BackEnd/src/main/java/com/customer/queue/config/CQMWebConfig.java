package com.customer.queue.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//import com.customer.queue.ymlfactory.YamlPropertyLoaderFactory;

//@Configuration
//@EnableWebMvc
//@PropertySource(value = "file:${PROPERTY_PATH}", factory = YamlPropertyLoaderFactory.class)
public class CQMWebConfig{
	Logger log=LoggerFactory.getLogger(CQMWebConfig.class);
	@Value("${finflowz.datasource.url}")
	public String dataSourceUrl;

	@Value("${finflowz.datasource.username}")
	public String dataSourceUsername;

	@Value("${finflowz.datasource.password}")
	public String dataSourcePassword;

	@Value("${finflowz.datasource.driver}")
	public String dataSourceDriver;
	
//	@Bean
//	@Primary
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(dataSourceDriver);
//		dataSource.setUrl(dataSourceUrl);
//		dataSource.setUsername(dataSourceUsername);
//		dataSource.setPassword(dataSourcePassword);
//		return dataSource;
//	}
	

}
