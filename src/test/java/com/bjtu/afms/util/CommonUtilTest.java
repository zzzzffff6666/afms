package com.bjtu.afms.util;

import com.bjtu.afms.utils.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class CommonUtilTest {

    @Test
    public void test() throws NoSuchAlgorithmException {
        String code = CommonUtil.generateVerifyCode();
        System.out.println("code: " + code);
        String salt = CommonUtil.generateSalt();
        System.out.println("salt: " + salt);
        String pwd1 = "12sanjsu=";
        String pwd2 = "12sanjsi.";
        String pwd3 = "22sanjsu/";
        String encoded1 = CommonUtil.encode(pwd1, salt);
        System.out.println("encoded 1: " + encoded1);
        String encoded2 = CommonUtil.encode(pwd2, salt);
        System.out.println("encoded 2: " + encoded2);
        String encoded3 = CommonUtil.encode(pwd3, salt);
        System.out.println("encoded 3: " + encoded3);
        System.out.println("pwd1 == encoded1 ? " + CommonUtil.matches(pwd1, salt, encoded1));
        System.out.println("pwd1 == encoded2 ? " + CommonUtil.matches(pwd1, salt, encoded2));
        System.out.println("pwd1 == encoded3 ? " + CommonUtil.matches(pwd1, salt, encoded3));
        System.out.println("pwd2 == encoded1 ? " + CommonUtil.matches(pwd2, salt, encoded1));
        System.out.println("pwd2 == encoded2 ? " + CommonUtil.matches(pwd2, salt, encoded2));
        System.out.println("pwd2 == encoded3 ? " + CommonUtil.matches(pwd2, salt, encoded3));
        System.out.println("pwd3 == encoded1 ? " + CommonUtil.matches(pwd3, salt, encoded1));
        System.out.println("pwd3 == encoded2 ? " + CommonUtil.matches(pwd3, salt, encoded2));
        System.out.println("pwd3 == encoded3 ? " + CommonUtil.matches(pwd3, salt, encoded3));
    }

    @Test
    public void test2() throws NoSuchAlgorithmException {
        String salt = CommonUtil.generateSalt();
        System.out.println("salt: " + salt);
        String pwd = "bjtu-afms";
        String encoded = CommonUtil.encode(pwd, salt);
        System.out.println(encoded);
    }
}
