package com.bjtu.afms.controller;

import com.bjtu.afms.biz.UserBiz;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.param.LoginParam;
import com.bjtu.afms.web.qo.UserQO;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return Result.error("手机号或密码错误");
        }
        if (!CommonUtil.matches(param.getCredential(), record.getSalt(), record.getPassword())) {
            return Result.error("手机号或密码错误");
        }
        UserQO user = new UserQO(record.getId(), record.getName(), record.getPhone());
        session.setAttribute("user", user);
        return Result.ok();
    }

    @PostMapping("/login/verify")
    public Result loginVerify(HttpSession session, @RequestParam LoginParam param) {
        User record = userService.selectUserForLogin(param.getPhone());
        if (record == null) {
            return Result.error("该用户不存在，请联系管理员进行注册");
        }
        boolean suc = verifyService.matchVerify(param.getPhone(), param.getCredential());
        if (suc) {
            UserQO user = new UserQO(record.getId(), record.getName(), record.getPhone());
            session.setAttribute("user", user);
            return Result.ok();
        } else {
            return Result.error("手机号或验证码错误");
        }
    }

}
