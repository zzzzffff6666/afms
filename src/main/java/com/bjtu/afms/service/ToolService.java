package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.bjtu.afms.utils.ConfigUtil;
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
import java.util.Map;

@Service
@Slf4j
public class ToolService {

    @Resource
    private ConfigUtil configUtil;

    private Client client;

    @PostConstruct
    public void init() throws Exception {
        Config config = new Config()
                .setAccessKeyId(configUtil.getAccessKeyId())
                .setAccessKeySecret(configUtil.getAccessKeySecret());
        config.endpoint = configUtil.getDomain();
        client = new Client(config);
    }

    public void sendVerify(String phone, Map<String, String> param) throws Exception {
        log.info("send verify to {phone}, code:{}", param.get("code"));
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(configUtil.getSignName())
                .setTemplateCode(configUtil.getTemplateCodeVerify())
                .setPhoneNumbers(phone)
                .setTemplateParam(JSON.toJSONString(param));
        SendSmsResponse response = client.sendSms(request);
        log.info("send status:{}", response.body.getCode());
    }

    public void sendAlert(String phone, Map<String, String> param) throws Exception {
        log.info("send verify to {phone}, alertName={}", param.get("alertName"));
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(configUtil.getSignName())
                .setTemplateCode(configUtil.getTemplateCodeAlert())
                .setPhoneNumbers(phone)
                .setTemplateParam(JSON.toJSONString(param));
        SendSmsResponse response = client.sendSms(request);
        log.info("send status:{}", response.body.getCode());
    }

    public void sendRemind(String phone, Map<String, String> param) throws Exception {
        log.info("send verify to {phone}, remindName={}", param.get("remindName"));
        SendSmsRequest request = new SendSmsRequest()
                .setSignName(configUtil.getSignName())
                .setTemplateCode(configUtil.getTemplateCodeRemind())
                .setPhoneNumbers(phone)
                .setTemplateParam(JSON.toJSONString(param));
        SendSmsResponse response = client.sendSms(request);
        log.info("send status:{}", response.body.getCode());
    }

    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
}
