package com.example.loan.config;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.loan.util.LoanJwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class LoanSecurityConfig {

    private final LoanJwtUtil jwtUtil;

    public LoanSecurityConfig(LoanJwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .anonymous(anon -> anon.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/health", "/actuator/**").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                    .requestMatchers("/loan/css/**", "/loan/js/**", "/loan/img/**").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, exAuth) -> {
                        res.setContentType("application/json;charset=UTF-8");
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"인증이 필요합니다\"}");
                    })
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ 단일 CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * ✅ 쿠키 기반 JWT 직접 파싱 전용 필터
     */
    public static class JwtAuthenticationFilter extends OncePerRequestFilter {
        private final LoanJwtUtil jwtUtil;
        private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

        public JwtAuthenticationFilter(LoanJwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

        
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain filterChain) throws ServletException, IOException {

           log.debug("🔍 JwtAuthenticationFilter 진입: {}", request.getRequestURI());

           String token = null;

           // 1. Authorization 헤더 우선
           String authHeader = request.getHeader("Authorization");
           log.debug("Authorization 헤더: {}", authHeader);
           if (authHeader != null && authHeader.startsWith("Bearer ")) {
               token = authHeader.substring(7);
               log.debug("Bearer 토큰 발견: 있음");
           }

           // 2. 쿠키에서 jwt-token 조회
           if (token == null && request.getCookies() != null) {
               log.debug("쿠키에서 JWT 토큰 검색 중...");
               for (Cookie cookie : request.getCookies()) {
                   log.debug("쿠키 발견: {} = {}", cookie.getName(), cookie.getValue());
                   if ("jwt-token".equals(cookie.getName())) {
                       token = cookie.getValue();
                       log.debug("쿠키에서 JWT 토큰 발견");
                       break;
                   }
               }
           }

           log.debug("최종 JWT 토큰: {}", token != null ? "있음" : "없음");

           if (token != null) {
               try {
                   log.debug("JWT 토큰 검증 시작");
                   if (jwtUtil.validateToken(token)) {
                       String role = jwtUtil.extractRole(token);
                       if (role == null) role = "USER";
                       String username = jwtUtil.getUsernameFromToken(token);
                       
                       log.debug("JWT 검증 성공 - Username: {}, Role: {}", username, role);

                       if (SecurityContextHolder.getContext().getAuthentication() == null) {
                           SimpleGrantedAuthority authority =
                                   new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role);
                           UsernamePasswordAuthenticationToken authToken =
                                   new UsernamePasswordAuthenticationToken(username, null,
                                           Collections.singletonList(authority));
                           SecurityContextHolder.getContext().setAuthentication(authToken);
                           log.debug("SecurityContext에 인증 정보 설정 완료");
                       }
                   } else {
                       log.debug("JWT 토큰 검증 실패");
                   }
               } catch (Exception e) {
                   log.error("❌ JWT 토큰 처리 중 오류: {}", e.getMessage());
               }
           } else {
               log.debug("JWT 토큰이 없습니다");
           }

           filterChain.doFilter(request, response);
        }
}
}