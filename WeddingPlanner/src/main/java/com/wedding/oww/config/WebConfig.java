package com.wedding.oww.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wedding.oww.security.JwtAuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ 선택 주입: 빈이 없어도 애플리케이션이 뜹니다.
    @Autowired(required = false)
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Autowired(required = false)
    private PlannerAuthInterceptor plannerAuthInterceptor;

    @Value("${app.auth.bypass:false}")
    private boolean authBypass;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authBypass) {
            System.out.println("🔓 [STANDALONE] app.auth.bypass=true → 보안 인터셉터 미등록");
            return; // 단독 실행 모드: 인증 완전 우회
        }

        if (jwtAuthInterceptor != null) {
            registry.addInterceptor(jwtAuthInterceptor)
                    .addPathPatterns("/planner/**")
                    .excludePathPatterns("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico");
        }

        if (plannerAuthInterceptor != null) {
            registry.addInterceptor(plannerAuthInterceptor)
                    .addPathPatterns("/planner/**")
                    .excludePathPatterns("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico");
        }
    }
}
