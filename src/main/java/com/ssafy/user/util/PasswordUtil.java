package com.ssafy.user.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    // salt를 생성하는 메소드
    public static String generateSalt() {
        //SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        String str = "ssafy";
        salt = str.getBytes();
        //random.nextBytes(salt);
        return bytesToHex(salt);
    }

    // 문자열과 salt를 사용하여 SHA-256 해시를 생성하는 메소드
    public static String generateHash(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(hexToBytes(salt));
        byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashedPassword);
    }

    // 바이트 배열을 16진수 문자열로 변환하는 메소드
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // 16진수 문자열을 바이트 배열로 변환하는 메소드
    private static byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return bytes;
    }

}
