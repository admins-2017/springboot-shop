package com.zhike.core.configuration;

import com.zhike.core.interceptors.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册权限过滤器到容器
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    /**
     * 添加一组过滤器到容器中
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        注册过滤器
        registry.addInterceptor(this.getPermissionInterceptor());
    }
}
