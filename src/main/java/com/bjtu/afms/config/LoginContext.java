package com.bjtu.afms.config;

import com.bjtu.afms.qo.UserQO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhang
 * @date: 2022/2/15 19:34
 * @description:
 */
@Slf4j
public class LoginContext {
    private static ThreadLocal<UserQO> userInfo = new ThreadLocal<>();

    public static void setUser(UserQO user) {
        log.info("当前线程 --- [{}] --- 设置用户 {} ", Thread.currentThread().getName(), user);
        userInfo.set(user);
    }

    public static UserQO getUser() {
        return userInfo.get();
    }

    public static Integer getUserId() {
        return userInfo.get().getId();
    }

    public static void removeUser() {
        userInfo.remove();
    }
}
