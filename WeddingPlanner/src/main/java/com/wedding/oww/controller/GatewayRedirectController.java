// src/main/java/com/wedding/oww/controller/GatewayRedirectController.java
package com.wedding.oww.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//✅ 단독 실행(bypass=true)일 때는 이 컨트롤러 빈을 만들지 않음
@ConditionalOnProperty(name = "app.auth.bypass", havingValue = "false", matchIfMissing = true)

//8888 로그인 선택 시 게이트웨이 /login으로 보내는 controller
@Controller
@RequiredArgsConstructor
public class GatewayRedirectController {

    @Value("${app.gateway-base-url}")
    private String gatewayBaseUrl;

    @Value("${app.login-path:/login}")
    private String loginPath;

    // 사용자가 8888/login 으로 들어오면 8201/login 으로 보내기
    @GetMapping("/login")
    public void redirectLogin(HttpServletResponse res) throws IOException {
        res.sendRedirect(gatewayBaseUrl + loginPath);
    }
}
