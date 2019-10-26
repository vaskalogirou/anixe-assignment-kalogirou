package com.kalogirou.anixe.aop.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

@Aspect
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final Environment env;

	public LoggingAspect(Environment env) {
		this.env = env;
	}

	@Pointcut("within(com.kalogirou.anixe.controller..*)")
	public void applicationPackagePointcut() {
	}

	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();
		String signatureName = signature.getName();
		String declaringName = signature.getDeclaringTypeName();
		String argsStr = Arrays.toString(joinPoint.getArgs());

		log.info("Enter: {}.{}() with argument[s] = {}", declaringName, signatureName, argsStr);
		try {
			Object result = joinPoint.proceed();
			log.info("Exit: {}.{}() with result = {}", declaringName, signatureName, result);
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", argsStr, declaringName, signatureName);
			throw e;
		}
	}
}
