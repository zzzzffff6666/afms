package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.bjtu.afms.config.ConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class SmsService {

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
}
