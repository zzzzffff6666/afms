package com.bjtu.afms.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class RandomUtil {

    private static int verifyCodeLength;

    private static int passwordSaltLength;

    private static int passwordHashTimes;

    public static String generateVerifyCode() {
        return RandomStringUtils.random(verifyCodeLength, false, true);
    }

    public static String generateSalt() {
        return RandomStringUtils.random(passwordSaltLength, true, true);
    }

    public static String encode(String origin, String salt) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (salt != null) {
            md5.reset();
            md5.update(salt.getBytes());
        }
        byte[] hashed = md5.digest(origin.getBytes());
        for (int i = 1; i < passwordHashTimes; ++i) {
            md5.reset();
            hashed = md5.digest(hashed);
        }
        return Hex.encodeHexString(hashed);
    }

    public static boolean matches(String rawPassword, String salt, String encodedPassword) throws NoSuchAlgorithmException {
        String encoded = encode(rawPassword, salt);
        return encoded.equals(encodedPassword);
    }

    @Value("${afms.verify-code-length}")
    public void setVerifyCodeLength(int verifyCodeLength) {
        RandomUtil.verifyCodeLength = verifyCodeLength;
    }

    @Value("${afms.password-salt-length}")
    public void setPasswordSaltLength(int passwordSaltLength) {
        RandomUtil.passwordSaltLength = passwordSaltLength;
    }

    @Value("${afms.password-hash-times}")
    public void setPasswordHashTimes(int passwordHashTimes) {
        RandomUtil.passwordHashTimes = passwordHashTimes;
    }
}
