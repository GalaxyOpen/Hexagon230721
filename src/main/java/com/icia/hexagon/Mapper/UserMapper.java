package com.icia.hexagon.Mapper;

import com.icia.hexagon.Config.Security.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    /**
     * 사용자 정보 조회 메서드
     *
     * @param insertedId 조회할 사용자의 ID
     * @return 사용자 정보를 담고 있는 UserInfo 객체
     */
    public UserInfo getUserInfo(String insertedId) {
        // 사용자가 입력한 아이디를 그대로 사용하여 memberId 저장
        // 비밀번호와 역할은 고정값으로 설정
        String roles = "USER";
        if ("admin".equals(insertedId)) {
            roles = "ADMIN";
        }

        return UserInfo.builder()
                .id(insertedId)
                .pw("d404559f602eab6fd602ac7680dacbfaadd13630335e951f097af3900e9de176b6db28512f2e000b9d04fba5133e8b1c6e8df59db3a8ab9d60be4b97cc9e81db")
                .roles(roles)
                .build();
    }

}
