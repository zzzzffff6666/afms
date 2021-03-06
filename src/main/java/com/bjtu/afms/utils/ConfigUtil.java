package com.bjtu.afms.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConfigUtil {

    @Value("${afms.page-size}")
    private int pageSize;

    @Value("${afms.qr-code.width}")
    private int qrCodeWidth;

    @Value("${afms.qr-code.height}")
    private int qrCodeHeight;

    @Value("${afms.default.maintain-interval-days}")
    private int defaultMaintainInterval;

    @Value("${sms.access-key-id}")
    private String accessKeyId;

    @Value("${sms.access-key-secret}")
    private String accessKeySecret;

    @Value("${sms.domain}")
    private String domain;

    @Value("${sms.sign-name}")
    private String signName;

    @Value("${sms.template-code.verify}")
    private String templateCodeVerify;

    @Value("${sms.template-code.alert}")
    private String templateCodeAlert;

    @Value("${sms.template-code.remind}")
    private String templateCodeRemind;
}
