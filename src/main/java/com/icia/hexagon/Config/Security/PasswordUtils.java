package com.icia.hexagon.Config.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 비밀번호를 암호화하여 반환하는 메소드
    public static String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // 입력된 비밀번호와 암호화된 비밀번호가 일치하는지 확인하는 메소드
    public static boolean matchPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

}
