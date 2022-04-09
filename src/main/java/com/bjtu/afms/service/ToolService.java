package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.bjtu.afms.model.User;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.ListUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ToolService {

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private UserService userService;

    private Client client;

    @PostConstruct
    public void init() throws Exception {
        Config config = new Config()
                .setAccessKeyId(configUtil.getAccessKeyId())
                .setAccessKeySecret(configUtil.getAccessKeySecret());
        config.endpoint = configUtil.getDomain();
        client = new Client(config);
    }

    public void sendVerify(String phone, Map<String, String> param) {
        try {
            log.info("send verify to {}, code:{}", phone, param.get("code"));
            SendSmsRequest request = new SendSmsRequest()
                    .setSignName(configUtil.getSignName())
                    .setTemplateCode(configUtil.getTemplateCodeVerify())
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSON.toJSONString(param));
            SendSmsResponse response = client.sendSms(request);
            if (response.body.getCode().equals("OK")) {
                log.info("send success");
            } else {
                log.error("send failed: {}", response.body.getCode());
            }
        } catch (Exception e) {
            log.error("send meet exception: " + e.getMessage());
        }
    }

    public void sendAlert(String phone, Map<String, String> param) {
        try {
            log.info("send alert to {}, alertName={}", phone, param.get("alertName"));
            SendSmsRequest request = new SendSmsRequest()
                    .setSignName(configUtil.getSignName())
                    .setTemplateCode(configUtil.getTemplateCodeAlert())
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSON.toJSONString(param));
            SendSmsResponse response = client.sendSms(request);
            if (response.body.getCode().equals("OK")) {
                log.info("send success");
            } else {
                log.error("send failed: {}", response.body.getCode());
            }
        } catch (Exception e) {
            log.error("send meet exception: " + e.getMessage());
        }
    }

    public void sendAlert(int id, Map<String, String> param) {
        User user = userService.selectUser(id);
        if (user == null) {
            return;
        }
        sendAlert(user.getPhone(), param);
    }

    public void sendAlert(List<String> phoneList, Map<String, String> param) {
        try {
            int size = phoneList.size();
            log.info("batch send alert to {}, alertName={}", phoneList, param.get("alertName"));
            SendBatchSmsRequest request = new SendBatchSmsRequest()
                    .setSignNameJson(JSON.toJSONString(ListUtil.copyElement(configUtil.getSignName(), size)))
                    .setTemplateCode(configUtil.getTemplateCodeAlert())
                    .setPhoneNumberJson(JSON.toJSONString(phoneList))
                    .setTemplateParamJson(JSON.toJSONString(ListUtil.copyElement(param, size)));
            SendBatchSmsResponse response = client.sendBatchSms(request);
            if (response.body.getCode().equals("OK")) {
                log.info("send success");
            } else {
                log.error("send failed: {}", response.body.getCode());
            }
        } catch (Exception e) {
            log.error("send meet exception: " + e.getMessage());
        }
    }

    public void batchSendAlert(List<Integer> userIdList, Map<String, String> param) {
        List<String> phoneList = userService.selectPhoneList(userIdList);
        sendAlert(phoneList, param);
    }


    public void sendRemind(String phone, Map<String, String> param) {
        try {
            log.info("send remind to {}, remindName={}", phone, param.get("remindName"));
            SendSmsRequest request = new SendSmsRequest()
                    .setSignName(configUtil.getSignName())
                    .setTemplateCode(configUtil.getTemplateCodeRemind())
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSON.toJSONString(param));
            SendSmsResponse response = client.sendSms(request);
            if (response.body.getCode().equals("OK")) {
                log.info("send success");
            } else {
                log.error("send failed: {}", response.body.getCode());
            }
        } catch (Exception e) {
            log.error("send meet exception: " + e.getMessage());
        }
    }

    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
