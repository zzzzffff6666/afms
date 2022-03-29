package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.biz.UserBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.User;
import com.bjtu.afms.service.UserService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.ModifyPasswordParam;
import com.bjtu.afms.web.param.ModifyPhoneParam;
import com.bjtu.afms.web.param.query.UserQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Component
public class UserBizImpl implements UserBiz {

    @Resource
    private UserService userService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<User> getUserList(UserQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<User> pageInfo = new PageInfo<>(userService.selectUserList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean modifyPhone(ModifyPhoneParam param, HttpSession session) {
        User me = userService.selectUser(LoginContext.getUserId());
        if (me == null) {
            session.removeAttribute("user");
            throw new BizException(APIError.USER_NOT_EXIST);
        } else {
            if (userService.exist(param.getPhoneNew())) {
                throw new BizException(APIError.PHONE_ALREADY_EXIST);
            } else {
                if (verifyService.matchVerify(me.getPhone(), param.getCodeOld())
                        && verifyService.matchVerify(param.getPhoneNew(), param.getCodeNew())) {
                    User user = new User();
                    user.setId(LoginContext.getUserId());
                    user.setPhone(param.getPhoneNew());
                    return userService.updateUser(user) == 1;
                } else {
                    throw new BizException(APIError.VERIFY_ERROR);
                }
            }
        }
    }

    @Override
    @Transactional
    public boolean modifyPassword(ModifyPasswordParam param, HttpSession session) throws BizException, NoSuchAlgorithmException {
        User me = userService.selectUserForLogin(LoginContext.getUser().getPhone());
        if (me == null) {
            session.removeAttribute("user");
            throw new BizException(APIError.USER_NOT_EXIST);
        }
        if (param.getType() == 1) {
            if (CommonUtil.matches(param.getCredential(), me.getSalt(), me.getPassword())) {
                String salt = CommonUtil.generateSalt();
                String password = CommonUtil.encode(param.getPasswordNew(), salt);
                return userService.updatePassword(LoginContext.getUserId(), password, salt) == 1;
            } else {
                throw new BizException(APIError.PASSWORD_ERROR);
            }
        } else {
            if (verifyService.matchVerify(LoginContext.getUser().getPhone(), param.getCredential())) {
                String salt = CommonUtil.generateSalt();
                String password = CommonUtil.encode(param.getPasswordNew(), salt);
                return userService.updatePassword(LoginContext.getUserId(), password, salt) == 1;
            } else {
                throw new BizException(APIError.VERIFY_ERROR);
            }
        }
    }

    @Override
    @Transactional
    public boolean adminModifyPhone(ModifyPhoneParam param) {
        if (verifyService.matchVerify(param.getPhoneNew(), param.getCodeNew())) {
            User user = new User();
            user.setId(param.getId());
            user.setPhone(param.getPhoneNew());
            return userService.updateUser(user) == 1;
        } else {
            throw new BizException(APIError.VERIFY_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean insertUser(User user) {
        if (userService.insertUser(user) == 1) {
            permissionBiz.initUserPermission(user.getId());
            logBiz.saveLog(DataType.USER, user.getId(), OperationType.INSERT_USER,
                    null, JSON.toJSONString(user));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyUserInfo(User user) {
        User record = new User();
        record.setId(user.getId());
        record.setName(user.getName());
        record.setCardId(user.getCardId());
        return userService.updateUser(record) == 1;
    }

    @Override
    @Transactional
    public boolean modifyUserStatus(int id, int status) {
        User old = userService.selectUser(id);
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        if (userService.updateUser(user) == 1) {
            logBiz.saveLog(DataType.USER, id, OperationType.UPDATE_USER_STATUS,
                    JSON.toJSONString(old), JSON.toJSONString(user));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(int userId) {
        User old = userService.selectUser(userId);
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }

        if (userService.deleteUser(userId) == 1) {
            permissionBiz.deleteUserPermission(userId);
            logBiz.saveLog(DataType.USER, userId, OperationType.DELETE_USER,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
