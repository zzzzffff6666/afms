package com.bjtu.afms.config;

import com.bjtu.afms.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: zhang
 * @date: 2022/2/16 22:05
 * @description:
 */
@Configuration
public class AFMSWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/login","/css/**","/img/**","/js/**");
    }
}
