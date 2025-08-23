package com.example.loan.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;


@Component
public class jwtutil {
	 private final String SECRET_KEY = "myBankingSecretKey123456789012345678901234567890";
	 
	    public Claims validateAndGetClaims(String token) throws Exception {
	        try {
	            return Jwts.parser()
	                    .setSigningKey(SECRET_KEY)
	                    .parseClaimsJws(token)
	                    .getBody();
	        } catch (SignatureException e) {
	            throw new Exception("유효하지 않은 JWT 서명");
	        } catch (Exception e) {
	            throw new Exception("JWT 토큰 오류: " + e.getMessage());
	        }
	    }
	}

