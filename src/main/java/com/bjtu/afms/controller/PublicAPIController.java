package com.bjtu.afms.controller;

import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.VerifyStatus;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.model.Verify;
import com.bjtu.afms.service.LogService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import com.bjtu.afms.utils.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PublicAPIController {

    @Resource
    private VerifyService verifyService;

    @Resource
    private LogService logService;

    @GetMapping("/api/verify/get")
    public Result sendVerify(@RequestParam("phone") String phone) {
        if (CommonUtil.checkPhone(phone)) {
            String code = CommonUtil.generateVerifyCode();

            // send message, use aliyun api

            Verify verify = new Verify();
            verify.setPhone(phone);
            verify.setStatus(VerifyStatus.VALID.getId());
            verify.setCode(code);
            verify.setExpireTime(DateUtil.plusMinutes(2));
            verifyService.insertOrUpdate(verify);
            return Result.ok();
        }
        return Result.error("手机号码不存在");
    }

    @AuthCheck(auth = {AuthType.ADMIN}, owner = true, data = DataType.LOG)
    @PostMapping("/api/operation/rollback/{logId}")
    public Result rollbackOperation(@PathVariable("logId") int logId) {
        Log log = logService.selectLog(logId);
        return Result.ok();
    }
}
