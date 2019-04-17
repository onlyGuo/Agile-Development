package com.aiyi.tool.dev.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Resource
    private LoginUserRoleHandlerInterceptor loginUserRoleHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserRoleHandlerInterceptor).addPathPatterns("/**")
            .excludePathPatterns("**.css", "**.js", "**.jpg", "**.png", "**.gif", "/login/**", "/error", "/static/**");
        super.addInterceptors(registry);
    }
}
