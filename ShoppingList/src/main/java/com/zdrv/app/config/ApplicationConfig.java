package com.zdrv.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zdrv.app.filter.AuthFilter;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	// 認証用フィルタの有効化
	@Bean
	public FilterRegistrationBean<AuthFilter> authFilter() {
		var bean = new FilterRegistrationBean<>(new AuthFilter());
		bean.addUrlPatterns("/list/*");
		bean.addUrlPatterns("/item/*");
		return bean;
	}

	// バリデーション
	@Override
	public Validator getValidator() {
		var validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	public MessageSource messageSource() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");	// propertiesファイル名
		return messageSource;
	}

}
