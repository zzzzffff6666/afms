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
import com.bjtu.afms.web.qo.UserQO;
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
            UserQO user = new UserQO(record.getId(), record.getName(), record.getPhone());
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
    public Result modifySelfPhone(@RequestBody @Validated ModifyPhoneParam param, HttpSession session) {
        if (userBiz.modifyPhone(param, session)) {
            UserQO userQO = LoginContext.getUser();
            userQO.setPhone(param.getPhoneNew());
            session.setAttribute("user", userQO);
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
    public Result getUserInfo(@PathVariable("userId") int userId) {
        User user = userService.selectUser(userId);
        if (user != null) {
            return Result.ok(user);
        } else {
            return Result.error(APIError.NOT_FUND);
        }
    }

    @GetMapping({"/user/all", "/user/all/{page}"})
    public Result getAllUser(@RequestParam UserQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(userBiz.getUserList(param, page));
    }

    @GetMapping({"/user/list", "/user/list/page"})
    public Result getUserList(@RequestParam UserQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(userBiz.getUserList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/admin/user/insert")
    public Result registerUser(@RequestBody @Validated User user) throws NoSuchAlgorithmException {
        String salt = CommonUtil.generateSalt();
        String password = CommonUtil.generatePassword();
        String encodePassword = CommonUtil.encode(password, salt);
        user.setSalt(salt);
        user.setPassword(encodePassword);
        if (userService.insertUser(user) == 1) {
            return Result.ok("randomPassword", password);
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/admin/user/phone/modify")
    public Result modifyPhoneByAdmin(@RequestBody @Validated ModifyPhoneParam param) {
        if (userBiz.adminModifyPhone(param)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/admin/user/status/modify")
    public Result modifyStatusByAdmin(@RequestBody User user) {
        User record = new User();
        record.setId(user.getId());
        record.setStatus(user.getStatus());
        if (userService.updateUser(record) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/admin/user/info/modify")
    public Result modifyInfoByAdmin(@RequestBody User user) {
        User record = new User();
        record.setId(user.getId());
        record.setName(user.getName());
        record.setCardId(user.getCardId());
        if (userService.updateUser(record) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STUFF_MANAGER})
    @PostMapping("/admin/user/delete/{userId}")
    public Result deleteByAdmin(@PathVariable("userId") int userId) {
        if (userService.deleteUser(userId) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
