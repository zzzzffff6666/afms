package com.bjtu.afms.interceptor;

import com.bjtu.afms.config.LoginContext;
import com.bjtu.afms.qo.UserQO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: zhang
 * @date: 2022/2/15 19:49
 * @description:
 */
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
