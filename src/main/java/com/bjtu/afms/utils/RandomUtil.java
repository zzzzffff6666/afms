package com.bjtu.afms.utils;

import com.bjtu.afms.config.ConfigUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class RandomUtil {

    @Resource
    private ConfigUtil configUtil;

    public String generateVerifyCode() {
        return RandomStringUtils.random(configUtil.getCodeLength(), false, true);
    }

    public String generateSalt() {
        return RandomStringUtils.random(configUtil.getSaltLength(), true, true);
    }

    public String encode(String origin, String salt) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (salt != null) {
            md5.reset();
            md5.update(salt.getBytes());
        }
        byte[] hashed = md5.digest(origin.getBytes());
        for (int i = 1; i < configUtil.getHashTimes(); ++i) {
            md5.reset();
            hashed = md5.digest(hashed);
        }
        return Hex.encodeHexString(hashed);
    }

    public boolean matches(String rawPassword, String salt, String encodedPassword) throws NoSuchAlgorithmException {
        String encoded = encode(rawPassword, salt);
        return encoded.equals(encodedPassword);
    }
}
