package com.studysiba.common;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j
public class LogAspect {

    //@Around("execution(* com.studysiba.service.*.*ServiceImpl.*(..))")
    public Object logPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        long end = System.currentTimeMillis();

        log.info("=============================================================");
        log.info("Path : " +  type + "." + proceedingJoinPoint.getSignature().getName() + "()");
        log.info("Argument/Parameter : " + Arrays.toString(proceedingJoinPoint.getArgs()));
        log.info("Running Time : " + ( end - start ));
        log.info("=============================================================");

        return result;
    }


//    @Before("execution(* com.studysiba.*.*ServiceImpl.*(..))")
//    public void beforeLogging() {
//        //log.info("Start Method : " + joinPoint.getTarget().getClass() + " / " + joinPoint.getSignature() + " / " + Arrays.toString(joinPoint.getArgs()));
//        log.info("12e12e21e");
//    }

//    @After("execution(* com.studysiba.*.*ServiceImpl.*(..))")
//    public void afterLogging(JoinPoint joinPoint) {
//        log.info("End Method : " + joinPoint.getTarget().getClass() + " / " + joinPoint.getSignature() + " / " + Arrays.toString(joinPoint.getArgs()));
//    }
}
