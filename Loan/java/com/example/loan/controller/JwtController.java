package com.example.loan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loan.service.LoanService;
import com.example.loan.util.jwtutil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class JwtController {

	private final jwtutil jwtUtil;
	
	@Autowired
	private LoanService loanservice;
	
	public JwtController(jwtutil jwtutil) {
		this.jwtUtil = jwtutil;
	}
	
    @GetMapping("/loanmain")
    public String getUserInfo(HttpServletRequest request,Model model) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.getloanmain();
		model.addAttribute("products", products);
        String token = null;

        // 1. 쿠키에서 jwt-token 찾기
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt-token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }


        try {
            // 2. JWT 검증 + payload 추출
            Claims claims = jwtUtil.validateAndGetClaims(token);

            String name = (String) claims.get("name");
            String email = claims.getSubject();

            Map<String, String> result = new HashMap<>();
            result.put("name", name);
            result.put("email", email);
			return "LoanMain";

        } catch (Exception e) {
           
        }
		return "LoanMain";
    }
}
