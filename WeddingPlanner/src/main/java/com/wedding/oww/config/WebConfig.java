package com.wedding.oww.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wedding.oww.security.JwtAuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthInterceptor jwtAuthInterceptor;
    private final PlannerAuthInterceptor plannerAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns(
                        "/planner/diy",
                        "/planner/plan/**",
                        "/planner/confirm",
                        "/planner/package/confirm",
                        "/planner/draft/**",
                        "/planner/product/compare"
                )
                .excludePathPatterns(
                        "/planner/info",
                        "/planner/intro",
                        "/planner/category"
                );
    }
}
