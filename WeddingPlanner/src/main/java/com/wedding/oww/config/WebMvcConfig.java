package com.wedding.oww.config;

import com.wedding.oww.security.JwtAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 주의:
 * - 기존에 생성자 주입(@RequiredArgsConstructor 등)으로 JwtAuthInterceptor/PlannerAuthInterceptor를
 *   "필수"로 요구하고 있었다면, 그 생성자를 삭제하고 아래처럼 선택 주입으로 바꿉니다.
 * - 다른 설정(뷰 리졸버 등)이 함께 들어있다면 그대로 유지하시고,
 *   addInterceptors 부분만 아래처럼 바꾸세요.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // ✅ 선택 주입: 빈이 없어도 앱이 뜹니다.
    @Autowired(required = false)
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Autowired(required = false)
    private PlannerAuthInterceptor plannerAuthInterceptor;

    @Value("${app.auth.bypass:false}")
    private boolean authBypass;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authBypass) {
            System.out.println("🔓 [STANDALONE] app.auth.bypass=true → 모든 인증 인터셉터 미등록");
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
