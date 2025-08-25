package com.oww.oww1.service;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.UserVO;
import com.oww.oww1.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserVO findByEmail(String userEmail) {
        return userMapper.selectByEmail(userEmail);
    }
}
