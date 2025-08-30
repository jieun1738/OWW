package oww.banking.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oww.banking.util.BankingJwtUtil;

@Configuration
@EnableWebSecurity
public class BankingSecurityConfig implements WebMvcConfigurer {

    private final BankingJwtUtil jwtUtil;

    public BankingSecurityConfig(BankingJwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           TrustedGatewayFilter trustedGatewayFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/health", "/actuator/**").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                    .requestMatchers("/mainside.html").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, exAuth) -> {
                        res.setContentType("application/json;charset=UTF-8");
                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        res.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"인증이 필요합니다\"}");
                    })
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(trustedGatewayFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ 통합된 CORS 설정 (중복 제거)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 허용할 오리진 설정 (패턴 사용)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // 허용할 헤더
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // 자격 증명 허용 (쿠키, Authorization 헤더 등)
        configuration.setAllowCredentials(true);
        
        // 브라우저 캐시 시간
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    // ✅ WebMvc CORS 설정도 추가
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    public static class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final BankingJwtUtil jwtUtil;
        private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

        public JwtAuthenticationFilter(BankingJwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            log.info("🔍 JwtAuthenticationFilter 진입: {}", request.getRequestURI());

            String authHeader = request.getHeader("Authorization");
            log.info("Authorization 헤더: {}", authHeader != null ? "있음" : "없음");

            // 쿠키에서 jwt-token 읽기
            if ((authHeader == null || !authHeader.startsWith("Bearer ")) && request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("jwt-token".equals(cookie.getName())) {
                        authHeader = "Bearer " + cookie.getValue();
                        log.info("쿠키에서 JWT 토큰 발견");
                        break;
                    }
                }
            }

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    if (jwtUtil.validateToken(token)) {
                        String role = jwtUtil.extractRole(token);
                        String username = jwtUtil.getUsernameFromToken(token);

                        log.info("JWT 토큰 검증 성공: username={}, role={}", username, role);

                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                                    role.startsWith("ROLE_") ? role : "ROLE_" + role
                            );
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            
                            log.info("✅ JWT 인증 완료: {}", username);
                        }
                    } else {
                        log.warn("❌ JWT 토큰 검증 실패");
                    }
                } catch (Exception e) {
                    log.error("❌ JWT 토큰 처리 중 오류: {}", e.getMessage());
                }
            } else {
                log.warn("❌ JWT 토큰이 없음");
            }

            filterChain.doFilter(request, response);
        }
    }

    @Bean
    public TrustedGatewayFilter trustedGatewayFilter() {
        return new TrustedGatewayFilter();
    }

    @Order(1)
    public static class TrustedGatewayFilter extends OncePerRequestFilter {

        private static final Logger log = LoggerFactory.getLogger(TrustedGatewayFilter.class);

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {

            String requestURI = request.getRequestURI();
            log.info("🔍 TrustedGatewayFilter 요청 URI: {}", requestURI);

            // 필요할 때만 헤더 전체 덤프 (DEBUG 레벨)
            if (log.isDebugEnabled()) {
                logAllHeaders(request);
            }

            // 정적 리소스는 바로 통과
            if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/img/") || requestURI.equals("/favicon.ico")) {
                filterChain.doFilter(request, response);
                return;
            }

            // 이미 인증된 경우 → DEBUG 로만 표시
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                log.debug("이미 SecurityContext 인증 존재, 통과");
                filterChain.doFilter(request, response);
                return;
            }

            // Gateway 헤더
            String userNO = request.getHeader("x-user-no");
            String username = request.getHeader("x-username");
            String userRole = request.getHeader("x-user-role");
            String userEmail = request.getHeader("x-user-email");
            String authHeader = request.getHeader("Authorization");

            if (log.isDebugEnabled()) {
                log.debug("Gateway 헤더 확인: User-No={}, Username={}, Role={}, Email={}, Auth={}",
                        userNO, username, userRole, userEmail, authHeader != null ? "있음" : "없음");
            }

            // 헤더 조건 충족 시 인증 처리
            if (username != null && userRole != null &&
                authHeader != null && authHeader.startsWith("Bearer ")) {

                try {
                    String roleWithPrefix = userRole.startsWith("ROLE_") ? userRole : "ROLE_" + userRole;
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleWithPrefix);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    log.info("✅ Gateway 인증 성공: {} (UserNo: {}, Role: {})", username, userNO, userRole);

                    filterChain.doFilter(request, response);
                    return;

                } catch (Exception e) {
                    log.error("❌ Gateway 인증 처리 오류: {}", e.getMessage(), e);
                }
            }

            // 헤더 부족 → WARN
            log.warn("⚠️ Gateway 헤더 부족, JWT 필터로 위임: {}", requestURI);
            filterChain.doFilter(request, response);
        }

        private void logAllHeaders(HttpServletRequest request) {
            log.debug("=== 모든 요청 헤더 ===");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                log.debug("{}: {}", headerName, headerValue);
            }
            log.debug("========================");
        }
    }

}