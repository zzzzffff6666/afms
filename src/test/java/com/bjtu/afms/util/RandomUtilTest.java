package com.bjtu.afms.util;

import com.bjtu.afms.utils.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class RandomUtilTest {

    @Resource
    private RandomUtil randomUtil;

    @Test
    public void test() throws NoSuchAlgorithmException {
        String code = randomUtil.generateVerifyCode();
        System.out.println("code: " + code);
        String salt = randomUtil.generateSalt();
        System.out.println("salt: " + salt);
        String pwd1 = "12sanjsu=";
        String pwd2 = "12sanjsi.";
        String pwd3 = "22sanjsu/";
        String encoded1 = randomUtil.encode(pwd1, salt);
        System.out.println("encoded 1: " + encoded1);
        String encoded2 = randomUtil.encode(pwd2, salt);
        System.out.println("encoded 2: " + encoded2);
        String encoded3 = randomUtil.encode(pwd3, salt);
        System.out.println("encoded 3: " + encoded3);
        System.out.println("pwd1 == encoded1 ? " + randomUtil.matches(pwd1, salt, encoded1));
        System.out.println("pwd1 == encoded2 ? " + randomUtil.matches(pwd1, salt, encoded2));
        System.out.println("pwd1 == encoded3 ? " + randomUtil.matches(pwd1, salt, encoded3));
        System.out.println("pwd2 == encoded1 ? " + randomUtil.matches(pwd2, salt, encoded1));
        System.out.println("pwd2 == encoded2 ? " + randomUtil.matches(pwd2, salt, encoded2));
        System.out.println("pwd2 == encoded3 ? " + randomUtil.matches(pwd2, salt, encoded3));
        System.out.println("pwd3 == encoded1 ? " + randomUtil.matches(pwd3, salt, encoded1));
        System.out.println("pwd3 == encoded2 ? " + randomUtil.matches(pwd3, salt, encoded2));
        System.out.println("pwd3 == encoded3 ? " + randomUtil.matches(pwd3, salt, encoded3));
    }
}
