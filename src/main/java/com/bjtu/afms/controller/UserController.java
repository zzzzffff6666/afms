package com.bjtu.afms.controller;

import com.bjtu.afms.biz.UserBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.User;
import com.bjtu.afms.web.param.LoginParam;
import com.bjtu.afms.web.param.ModifyPasswordParam;
import com.bjtu.afms.web.param.ModifyPhoneParam;
import com.bjtu.afms.web.param.query.UserQueryParam;
import com.bjtu.afms.config.context.LoginUser;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private UserBiz userBiz;

    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginParam param, HttpSession session) throws Exception {
        User record = userService.selectUserForLogin(param.getPhone());
        if (record == null) {
            return Result.error(APIError.LOGIN_ERROR);
        }
        if (CommonUtil.matches(param.getCredential(), record.getSalt(), record.getPassword())) {
            LoginUser user = new LoginUser(record.getId(), record.getName(), record.getPhone());
            session.setAttribute("user", user);
            return Result.ok();
        } else {
            return Result.error(APIError.LOGIN_ERROR);
        }
    }

    @PostMapping("/login/verify")
    public Result loginVerify(@RequestBody @Validated LoginParam param, HttpSession session) {
        User record = userService.selectUserForLogin(param.getPhone());
        if (record == null) {
            return Result.error(APIError.USER_NOT_REGISTER);
        }
        boolean suc = verifyService.matchVerify(param.getPhone(), param.getCredential());
        if (suc) {
            LoginUser user = new LoginUser(record.getId(), record.getName(), record.getPhone());
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

    @PostMapping("/my/info/modify")
    public Result modifySelfInfo(@RequestBody User user) {
        user.setId(LoginContext.getUserId());
        if (userBiz.modifyUserInfo(user)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/my/phone/modify")
    public Result modifySelfPhone(@RequestBody @Validated ModifyPhoneParam param, HttpSession session) {
        if (userBiz.modifyPhone(param, session)) {
            LoginUser loginUser = LoginContext.getUser();
            loginUser.setPhone(param.getPhoneNew());
            session.setAttribute("user", loginUser);
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/my/password/modify")
    public Result modifySelfPassword(@RequestBody @Validated ModifyPasswordParam param, HttpSession session)
            throws NoSuchAlgorithmException {
        if (userBiz.modifyPassword(param, session)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @GetMapping("/user/info/{userId}")
    public Result getUserInfo(@PathVariable("userId") int id) {
        User user = userService.selectUser(id);
        if (user != null) {
            return Result.ok(user);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/user/all", "/user/all/{page}"})
    public Result getAllUser(UserQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(userBiz.getUserList(param, page));
    }

    @GetMapping({"/user/list", "/user/list/{page}"})
    public Result getUserList(UserQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(userBiz.getUserList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/user/insert")
    public Result registerUser(@RequestBody @Validated User user) throws NoSuchAlgorithmException {
        String salt = CommonUtil.generateSalt();
        String password = CommonUtil.generatePassword();
        String encodePassword = CommonUtil.encode(password, salt);
        user.setSalt(salt);
        user.setPassword(encodePassword);
        if (userBiz.insertUser(user)) {
            return Result.ok("randomPassword", password);
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/user/phone/modify")
    public Result modifyUserPhone(@RequestBody @Validated ModifyPhoneParam param) {
        if (userBiz.adminModifyPhone(param)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/user/status/modify")
    public Result modifyUserStatus(@RequestParam("id") int id, @RequestParam("status") int status) {
        if (userBiz.modifyUserStatus(id, status)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/user/info/modify")
    public Result modifyUserInfo(@RequestBody User user) {
        if (userBiz.modifyUserInfo(user)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/user/delete/{userId}")
    public Result deleteUser(@PathVariable("userId") int id) {
        if (userBiz.deleteUser(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
