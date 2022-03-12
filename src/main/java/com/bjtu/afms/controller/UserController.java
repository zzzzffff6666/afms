package com.bjtu.afms.controller;

import com.bjtu.afms.biz.UserBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.param.LoginParam;
import com.bjtu.afms.web.param.ModifyPhoneParam;
import com.bjtu.afms.web.qo.UserQO;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private UserBiz userBiz;

    @PostMapping("/login")
    public Result login(HttpSession session, @RequestBody LoginParam param) throws Exception {
        User record = userService.selectUserForLogin(param.getPhone());
        if (record == null) {
            return Result.error(APIError.LOGIN_ERROR);
        }
        if (CommonUtil.matches(param.getCredential(), record.getSalt(), record.getPassword())) {
            UserQO user = new UserQO(record.getId(), record.getName(), record.getPhone());
            session.setAttribute("user", user);
            return Result.ok();
        } else {
            return Result.error(APIError.LOGIN_ERROR);
        }
    }

    @PostMapping("/login/verify")
    public Result loginVerify(HttpSession session, @RequestBody LoginParam param) {
        User record = userService.selectUserForLogin(param.getPhone());
        if (record == null) {
            return Result.error(APIError.USER_NOT_REGISTER);
        }
        boolean suc = verifyService.matchVerify(param.getPhone(), param.getCredential());
        if (suc) {
            UserQO user = new UserQO(record.getId(), record.getName(), record.getPhone());
            session.setAttribute("user", user);
            return Result.ok();
        } else {
            return Result.error(APIError.VERIFY_ERROR);
        }
    }

    @GetMapping("/my/info")
    public Result getSelfInfo(HttpSession session) {
        User me = userService.selectUser(LoginContext.getUserId());
        if (me == null) {
            session.removeAttribute("user");
            return Result.error(APIError.USER_NOT_EXIST);
        } else {
            return Result.ok(me);
        }
    }

    @PostMapping("/my/phone/modify")
    public Result modifySelfPhone(HttpSession session, @RequestBody ModifyPhoneParam param) {
        User me = userService.selectUser(LoginContext.getUserId());
        if (me == null) {
            session.removeAttribute("user");
            return Result.error(APIError.USER_NOT_EXIST);
        } else {
            if (userService.exist(param.getPhoneNew())) {
                return Result.error(APIError.PHONE_EXIST);
            } else {
                if (verifyService.matchVerify(me.getPhone(), param.getCodeOld())
                        && verifyService.matchVerify(param.getPhoneNew(), param.getCodeNew())) {
                    User user = new User();
                    user.setId(LoginContext.getUserId());
                    user.setPhone(param.getPhoneNew());
                    if (userService.updateUser(user) == 1) {
                        UserQO userQO = LoginContext.getUser();
                        userQO.setPhone(param.getPhoneNew());
                        session.setAttribute("user", userQO);
                        return Result.ok();
                    } else {
                        return Result.error(APIError.UPDATE_ERROR);
                    }
                } else {
                    return Result.error(APIError.VERIFY_ERROR);
                }
            }
        }
    }
}
