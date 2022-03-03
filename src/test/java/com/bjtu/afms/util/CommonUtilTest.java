package com.bjtu.afms.util;

import com.bjtu.afms.utils.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class CommonUtilTest {

    @Resource
    private CommonUtil commonUtil;

    @Test
    public void test() throws NoSuchAlgorithmException {
        String code = commonUtil.generateVerifyCode();
        System.out.println("code: " + code);
        String salt = commonUtil.generateSalt();
        System.out.println("salt: " + salt);
        String pwd1 = "12sanjsu=";
        String pwd2 = "12sanjsi.";
        String pwd3 = "22sanjsu/";
        String encoded1 = commonUtil.encode(pwd1, salt);
        System.out.println("encoded 1: " + encoded1);
        String encoded2 = commonUtil.encode(pwd2, salt);
        System.out.println("encoded 2: " + encoded2);
        String encoded3 = commonUtil.encode(pwd3, salt);
        System.out.println("encoded 3: " + encoded3);
        System.out.println("pwd1 == encoded1 ? " + commonUtil.matches(pwd1, salt, encoded1));
        System.out.println("pwd1 == encoded2 ? " + commonUtil.matches(pwd1, salt, encoded2));
        System.out.println("pwd1 == encoded3 ? " + commonUtil.matches(pwd1, salt, encoded3));
        System.out.println("pwd2 == encoded1 ? " + commonUtil.matches(pwd2, salt, encoded1));
        System.out.println("pwd2 == encoded2 ? " + commonUtil.matches(pwd2, salt, encoded2));
        System.out.println("pwd2 == encoded3 ? " + commonUtil.matches(pwd2, salt, encoded3));
        System.out.println("pwd3 == encoded1 ? " + commonUtil.matches(pwd3, salt, encoded1));
        System.out.println("pwd3 == encoded2 ? " + commonUtil.matches(pwd3, salt, encoded2));
        System.out.println("pwd3 == encoded3 ? " + commonUtil.matches(pwd3, salt, encoded3));
    }
}
