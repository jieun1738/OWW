package com.oww.oww1.VO;

import lombok.Data;

@Data
public class UserVO {
    private String userEmail; // user_tb.user_email
    private String userName;
    private Integer socialType; // 0: google, 1: github
}
