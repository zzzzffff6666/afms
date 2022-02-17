package com.bjtu.afms.config.interceptor;

import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.qo.UserQO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserQO user = (UserQO) session.getAttribute("user");
        if (user != null) {
            LoginContext.setUser(user);
            return true;
        } else {
            response.sendRedirect("/login");
            return false;
        }
    }
}