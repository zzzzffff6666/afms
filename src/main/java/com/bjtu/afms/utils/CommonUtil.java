package com.bjtu.afms.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class CommonUtil {

    private final static int codeLength = 6;
    private final static int saltLength = 20;
    private final static int hashTimes = 3;
    private final static int passwordLength = 16;

    public static String generateVerifyCode() {
        return RandomStringUtils.random(codeLength, false, true);
    }

    public static String generateSalt() {
        return RandomStringUtils.random(saltLength, true, true);
    }

    public static String generatePassword() {
        return RandomStringUtils.random(passwordLength, true, true);
    }

    public static String encode(String origin, String salt) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (salt != null) {
            md5.reset();
            md5.update(salt.getBytes());
        }
        byte[] hashed = md5.digest(origin.getBytes());
        for (int i = 1; i < hashTimes; ++i) {
            md5.reset();
            hashed = md5.digest(hashed);
        }
        return Hex.encodeHexString(hashed);
    }

    public static boolean matches(String rawPassword, String salt, String encodedPassword) throws NoSuchAlgorithmException {
        String encoded = encode(rawPassword, salt);
        return encoded.equals(encodedPassword);
    }

    public static boolean checkPhone(String phone) {
        if (phone != null && !phone.isEmpty()) {
            return Pattern.matches("^1[3-9]\\d{9}$", phone);
        }
        return false;
    }
}
