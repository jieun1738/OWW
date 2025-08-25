package com.oww.oww1.VO;

import lombok.Data;

/**
 * 프래그먼트(header/nav/sidebar)에 전달할 사용자 뷰 모델
 * - 템플릿 호환을 위해 필드명 중복 제공: name/userName, email/userEmail
 */
@Data
public class UserModel {
    private String userEmail;
    private String email;     // 템플릿에서 email로 읽는 경우 대응
    private String userName;
    private String name;      // 템플릿에서 name으로 읽는 경우 대응
}
