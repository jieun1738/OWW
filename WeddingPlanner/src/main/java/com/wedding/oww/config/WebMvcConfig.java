package com.wedding.oww.config; // ← 프로젝트 패키지에 맞게

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final PlannerAuthInterceptor plannerAuthInterceptor;

    @Value("${app.auth.bypass:false}")
    private boolean authBypass;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authBypass) {
            System.out.println("🔓 [DEV] app.auth.bypass=true → PlannerAuthInterceptor 미등록(임시 공개 모드)");
            return; // ← 인증 인터셉터 등록 자체를 생략
        }

        // 평소처럼 보호 모드(원래 로직)
        registry.addInterceptor(plannerAuthInterceptor)
                .addPathPatterns("/planner/**")
                .excludePathPatterns(
                        "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico"
                );
    }
}
