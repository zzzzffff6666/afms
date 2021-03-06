package com.bjtu.afms.controller;

import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.VerifyStatus;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.model.Verify;
import com.bjtu.afms.service.CommonService;
import com.bjtu.afms.service.LogService;
import com.bjtu.afms.service.ToolService;
import com.bjtu.afms.service.VerifyService;
import com.bjtu.afms.utils.CommonUtil;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.DateUtil;
import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class PublicAPIController {

    @Resource
    private CommonService commonService;

    @Resource
    private ToolService toolService;

    @Resource
    private VerifyService verifyService;

    @Resource
    private LogService logService;

    @Resource
    private ConfigUtil configUtil;

    @GetMapping("/verify/get")
    public Result sendVerify(@RequestParam("phone") String phone) {
        if (CommonUtil.checkPhone(phone)) {
            String code = CommonUtil.generateVerifyCode();
            Map<String, String> param = new HashMap<>();
            param.put("code", code);
            toolService.sendVerify(phone, param);

            Verify verify = new Verify();
            verify.setPhone(phone);
            verify.setStatus(VerifyStatus.VALID.getId());
            verify.setCode(code);
            verify.setExpireTime(DateUtil.plusMinutes(2));
            verifyService.insertOrUpdate(verify);
            return Result.ok();
        }
        return Result.error(APIError.PHONE_ERROR);
    }

    @AuthCheck(auth = {AuthType.ADMIN}, owner = true, data = DataType.LOG)
    @PostMapping("/operation/rollback/{logId}")
    public Result rollbackOperation(@PathVariable("logId") int id) throws BizException {
        Log log = logService.selectLog(id);
        Assert.notNull(log, APIError.NOT_FOUND);
        if (commonService.rollbackOperation(log)) {
            return Result.ok();
        }
        return Result.error(APIError.OPERATION_ROLLBACK_FAILED);
    }

    @GetMapping("/QRCode/get")
    public Result getQRCode(@RequestParam("type") String type, @RequestParam("id") int id) {
        String url = "/" + type + "/info/" + id;
        try {
            String qrCode = toolService.getQRCode(url, configUtil.getQrCodeWidth(), configUtil.getQrCodeHeight());
            return Result.ok(qrCode);
        } catch (IOException | WriterException e) {
            return Result.error(e.getMessage());
        }
    }
}
