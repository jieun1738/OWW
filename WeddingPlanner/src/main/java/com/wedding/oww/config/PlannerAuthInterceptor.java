package com.wedding.oww.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wedding.oww.security.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlannerAuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil; // 기존 JwtUtil 사용 (수정하지 않음)

    @Value("${app.gateway-base-url}")
    private String gatewayBaseUrl;
    @Value("${app.login-path:/login}")
    private String loginPath;
    @Value("${app.post-login-path:/planner/info}")
    private String postLoginPath;

    private String getCookie(HttpServletRequest req, String name) {
        if (req.getCookies() == null) return null;
        for (Cookie c : req.getCookies()) {
            if (name.equals(c.getName())) return c.getValue();
        }
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        // 정적/공개 경로는 통과 (필요 시 추가)
        String uri = req.getRequestURI();
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/") ||
            uri.equals("/") || uri.startsWith("/error")) {
            return true;
        }

        // 플래너 영역만 보호
        if (!uri.startsWith("/planner/")) return true;

        String token = getCookie(req, "jwt-token");
        if (token == null || token.isBlank()) {
            // JWT 없으면 게이트웨이의 /login 으로 리다이렉트
            String continueUrl = gatewayBaseUrl + postLoginPath; // 로그인 후 사용자가 열어야 할 위치(안내용)
            String redirect = gatewayBaseUrl + loginPath + "?redirect=" +
                    URLEncoder.encode(continueUrl, StandardCharsets.UTF_8);
            res.sendRedirect(redirect);
            return false;
        }

        try {
            Claims claims = jwtUtil.validateAndGetClaims(token);
            // 컨트롤러/템플릿에서 바로 사용 가능
            req.setAttribute("userEmail", claims.getSubject());
            Object nm = claims.get("name");
            req.setAttribute("userName", nm == null ? "" : String.valueOf(nm));
            return true;
        } catch (Exception e) {
            // 유효하지 않은 토큰 → 재로그인 요구
            String continueUrl = gatewayBaseUrl + postLoginPath;
            String redirect = gatewayBaseUrl + loginPath + "?redirect=" +
                    URLEncoder.encode(continueUrl, StandardCharsets.UTF_8);
            res.sendRedirect(redirect);
            return false;
        }
    }
}
