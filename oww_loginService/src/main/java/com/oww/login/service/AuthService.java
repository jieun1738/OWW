package com.oww.login.service;

import com.oww.login.dto.AuthDto;
import com.oww.login.entity.User;
import com.oww.login.repository.UserRepository;
import com.oww.login.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public AuthDto.UserInfo getUserInfo(String email) {
        User user = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return AuthDto.UserInfo.from(user);
    }

    public AuthDto.SocialLoginResponse processSocialLogin(User user, boolean isNewUser) {
        // JWT 토큰 생성
        String accessToken = jwtUtil.generateToken(user.getUserNo(), user.getName(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserNo(), user.getName());

        log.info("소셜 로그인 처리: {} ({}) - 신규사용자: {}", 
                user.getEmail(), user.getProvider(), isNewUser);

        return AuthDto.SocialLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(AuthDto.UserInfo.from(user))
                .isNewUser(isNewUser)
                .build();
    }
}