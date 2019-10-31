package com.kalogirou.anixe.aop.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

	// this is NOT the default logger - it is a custom one, that writes to file
	// and we use it ONLY for the controller package where the user requests reside
	private final Logger logger = LoggerFactory.getLogger("file-logger");

	@Pointcut("within(com.kalogirou.anixe.controller..*)")
	public void applicationPackagePointcut() {
	}

	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature signature = joinPoint.getSignature();
		String signatureName = signature.getName();
		String declaringName = signature.getDeclaringTypeName();
		String argsStr = Arrays.toString(joinPoint.getArgs());

		logger.info("Enter: {}.{}() with argument[s] = {}", declaringName, signatureName, argsStr);
		try {
			Object result = joinPoint.proceed();
			logger.info("Exit: {}.{}() with result = {}", declaringName, signatureName, result);
			return result;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument: {} in {}.{}()", argsStr, declaringName, signatureName);
			throw e;
		}
	}
}
