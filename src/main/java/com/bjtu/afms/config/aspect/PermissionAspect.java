package com.bjtu.afms.config.aspect;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
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

    @Pointcut("@annotation(com.bjtu.afms.config.annotation.AuthCheck)")
    public void pointcut() {}

    @Around("pointcut() && @annotation(authCheck)")
    public Object aroundExecute(ProceedingJoinPoint pjp, AuthCheck authCheck) throws Throwable {
        Object result;
        Object[] objs = pjp.getArgs();
        if (hasPermission(objs[0], authCheck)) {
            try {
                result = pjp.proceed();
                return result;
            } catch (Throwable t) {
                log.error(t.getMessage());
                throw t;
            }
        } else {
            throw new BizException(APIError.NO_PERMISSION);
        }
    }

    public boolean hasPermission(Object object, AuthCheck authCheck) {
        boolean result = false;
        if (authCheck.auth().length > 0) {
            result = permissionService.hasPermission(LoginContext.getUserId(),
                    Arrays.stream(authCheck.auth()).map(AuthType::getId).collect(Collectors.toList()));
        }
        if (result) {
            return result;
        }

        if (authCheck.owner()) {
            Integer relateId;
            int type;
            if (authCheck.relate()) {
                relateId = ((JSONObject) object).getInteger("relateId");
                type = ((JSONObject) object).getInteger("type");
            } else {
                if (object instanceof Integer) {
                    relateId = (int) object;
                } else {
                    relateId = ((JSONObject) object).getInteger("id");
                    if (relateId == null) {
                        relateId = ((JSONObject) object).getInteger(authCheck.data().getName() + "Id");
                    }
                    if (relateId == null) {
                        throw new BizException(APIError.PARAMETER_ERROR);
                    }
                }
                type = authCheck.data().getId();
            }
            result = permissionService.isOwner(LoginContext.getUserId(), type, relateId);
        }
        return result;
    }
}
