package com.icia.hexagon.Config.Security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder  // 빌더 패턴을 사용할 수 있도록 함
@NoArgsConstructor  // 인자가 없는 생성자 생성
@AllArgsConstructor  // 모든 필드를 인자로 받는 생성자 생성
public class UserInfo {
    private String id;  // 사용자 ID
    private String pw;  // 사용자 비밀번호
    private String roles;  // 사용자 역할
}
