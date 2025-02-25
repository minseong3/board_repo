package com.cspi.notionboard.global.authentication;

import org.mindrot.jbcrypt.BCrypt;
public class PasswordUtil {
    // 비밀번호 해싱
    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // 비밀번호 검증
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
