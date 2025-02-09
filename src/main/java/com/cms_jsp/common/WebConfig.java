package com.cms_jsp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private FrontApiInterceptor interceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(interceptor).addPathPatterns("/**frontApi/**");
        
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("*")
	        .allowCredentials(false)
	        .maxAge(3600);
    }
	

}
