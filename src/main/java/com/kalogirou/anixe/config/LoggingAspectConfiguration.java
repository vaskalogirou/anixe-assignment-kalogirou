package com.kalogirou.anixe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.kalogirou.anixe.aop.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {
	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
