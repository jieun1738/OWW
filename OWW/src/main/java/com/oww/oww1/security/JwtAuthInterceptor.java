package com.oww.oww1.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    // 보호 경로에서만 사용: /planner/diy, /planner/plan/**, /planner/confirm, /planner/package/confirm
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt-token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }
        if (token == null || token.isBlank()) {
            // TODO: 로그인 URL 확정 시 교체
            response.sendRedirect("/login");
            return false;
        }
        try {
            Claims claims = jwtUtil.validateAndGetClaims(token);
            String email = claims.getSubject(); // sub
            request.setAttribute("userEmail", email);
            request.setAttribute("userName", claims.get("name", String.class));
            return true;
        } catch (Exception e) {
            // TODO: 필요 시 모달 알림 트리거 방식으로 리다이렉트
            response.sendRedirect("/login");
            return false;
        }
    }
}
