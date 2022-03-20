package com.bjtu.afms.biz;

import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.param.ModifyPasswordParam;
import com.bjtu.afms.web.param.ModifyPhoneParam;
import com.bjtu.afms.web.param.query.UserQueryParam;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

public interface UserBiz {
    Page<User> getUserList(UserQueryParam param, Integer page);

    boolean modifyPhone(ModifyPhoneParam param, HttpSession session);

    boolean modifyPassword(ModifyPasswordParam param, HttpSession session) throws BizException, NoSuchAlgorithmException;

    boolean adminModifyPhone(ModifyPhoneParam param);

    boolean insertUser(User user);

    boolean modifyUserInfo(User user);

    boolean modifyUserStatus(int id, int status);

    boolean deleteUser(int userId);
}
