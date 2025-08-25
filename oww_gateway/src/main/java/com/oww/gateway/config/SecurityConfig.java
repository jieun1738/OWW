package com.oww.gateway.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)  
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) 
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(exchanges -> exchanges
                        // ✅ 정적 리소스 허용
                        .pathMatchers("/", "/index.html", "/css/**", "/js/**", "/img/**").permitAll()
                        
                        // ✅ 인증 관련 경로 허용
                        .pathMatchers("/auth/**", "/login/**", "/oauth2/**").permitAll()
                        
                        // ✅ 헬스체크 허용
                        .pathMatchers("/health", "/test").permitAll()
                        
                        // ✅ Banking 경로 허용 (JWT 필터에서 인증 처리)
                        .pathMatchers("/banking/**", "/api/banking/**").permitAll()
                        
                        // ✅ MyPage 경로 허용 (JWT 필터에서 인증 처리)
                        .pathMatchers("/api/mypage/**").permitAll()
                        
                        // 나머지는 인증 필요
                        .anyExchange().authenticated()
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Gateway의 index.html만 제공 (main.css도 Gateway에서)
    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.resources("/**", new ClassPathResource("static/"));
    }
}