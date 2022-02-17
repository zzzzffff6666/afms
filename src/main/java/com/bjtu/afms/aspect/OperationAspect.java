package com.bjtu.afms.aspect;

import com.bjtu.afms.config.context.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class OperationAspect {

    @Before("execution(* com.bjtu.afms.mapper..*.insert*(..))")
    public void beforeInsert(JoinPoint joinPoint) throws Throwable {
        Object[] parameters = joinPoint.getArgs();
        for (Object obj : parameters) {
            try {
                Method method1 = obj.getClass().getMethod("setAddTime", Date.class);
                method1.invoke(obj, new Date());
                Method method2 = obj.getClass().getMethod("setAddUser", Integer.class);
                method2.invoke(obj, LoginContext.getUserId());
            } catch (Exception ignored) {
            }
        }
    }

    @Before("execution(* com.bjtu.afms.mapper..*.update*(..))")
    public void beforeUpdate(JoinPoint joinPoint) throws Throwable {
        Object[] parameters = joinPoint.getArgs();
        for (Object obj : parameters) {
            if (obj.getClass().getName().endsWith("Example")) {
                break;
            }
            try {
                Method method1 = obj.getClass().getMethod("setModTime", Date.class);
                method1.invoke(obj, new Date());
                Method method2 = obj.getClass().getMethod("setModUser", Integer.class);
                method2.invoke(obj, LoginContext.getUserId());
            } catch (Exception ignored) {
            }
        }
    }
}
