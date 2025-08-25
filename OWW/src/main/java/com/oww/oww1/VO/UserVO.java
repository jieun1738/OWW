package com.oww.oww1.VO;

import lombok.Data;

/** user_tb 매핑 VO (템플릿에서 ${user.userName} 사용) */
@Data
public class UserVO {
    private String userEmail; // user_tb.user_email
    private String userName;  // user_tb.user_name
    private int socialType;   // 0=google, 1=github
}
