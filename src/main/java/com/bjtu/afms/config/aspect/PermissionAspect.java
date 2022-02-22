package com.bjtu.afms.config.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.afms.config.annotation.PermissionCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.PermissionType;
import com.bjtu.afms.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class PermissionAspect {

    @Resource
    private PermissionService permissionService;

    @Pointcut("@annotation(com.bjtu.afms.config.annotation.PermissionCheck)")
    public void pointcut() {}

    @Around("pointcut() && @annotation(permissionCheck)")
    public Object beforeExecute(ProceedingJoinPoint pjp, PermissionCheck permissionCheck) throws Throwable {
        Object result = null;
        Object[] objs = pjp.getArgs();
        if (hasPermission(objs[0], permissionCheck)) {
            try {
                result = pjp.proceed();
            } catch (Throwable t) {
                log.error(t.getMessage());
                throw t;
            }
        }
        return result;
    }

    public boolean hasPermission(Object object, PermissionCheck permissionCheck) {
        boolean result = false;
        if (permissionCheck.owner()) {
            Integer relateId = ((JSONObject) object).getInteger("id");
            result = permissionService.isOwner(LoginContext.getUserId(), relateId);
            if (permissionCheck.permission().length == 0 || result) {
                return result;
            }
        }
        result = permissionService.hasPermission(LoginContext.getUserId(),
                Arrays.stream(permissionCheck.permission()).map(PermissionType::getId).collect(Collectors.toList()));
        return result;
    }
}
