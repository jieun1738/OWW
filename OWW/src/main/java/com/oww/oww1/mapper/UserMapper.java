package com.oww.oww1.mapper;

import com.oww.oww1.VO.UserVO;

public interface UserMapper {
    UserVO selectByEmail(String userEmail);
}
