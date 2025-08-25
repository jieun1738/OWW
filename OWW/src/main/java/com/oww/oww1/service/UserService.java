package com.oww.oww1.service;

import com.oww.oww1.VO.UserVO;

public interface UserService {
    UserVO findByEmail(String userEmail);
}
