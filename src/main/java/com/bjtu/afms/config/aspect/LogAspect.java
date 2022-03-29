package com.bjtu.afms.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getPointcut() {}

    @Around("getPointcut() && @annotation(get)")
    public Object getLog(ProceedingJoinPoint pjp, GetMapping get) throws Throwable {
        Object[] args = pjp.getArgs();
        String requestPath = Arrays.toString(get.value());
        log.info(String.format("请求---%s", requestPath));
        log.debug(String.format("参数---%s", Arrays.toString(args)));
        try {
            Object result = pjp.proceed();
            log.info(String.format("请求---%s---完成", requestPath));
            return result;
        } catch (Throwable t) {
            log.error(String.format("请求---%s---失败", requestPath));
            log.error(t.getMessage());
            throw t;
        }
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postPointcut() {}

    @Around("postPointcut() && @annotation(post)")
    public Object postLog(ProceedingJoinPoint pjp, PostMapping post) throws Throwable {
        Object[] args = pjp.getArgs();
        String requestPath = Arrays.toString(post.value());
        log.info(String.format("请求---%s", requestPath));
        log.debug(String.format("参数---%s", Arrays.toString(args)));
        try {
            Object result = pjp.proceed();
            log.info(String.format("请求---%s---完成", requestPath));
            return result;
        } catch (Throwable t) {
            log.error(String.format("请求---%s---失败", requestPath));
            log.error(t.getMessage());
            throw t;
        }
    }
}
