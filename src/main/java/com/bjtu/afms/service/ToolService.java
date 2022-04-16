package com.bjtu.afms.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.bjtu.afms.model.User;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.ListUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
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

    // 本地使用
    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        String format = "jpg";
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    // 发送给前台
    public String getQRCode(String text, int width, int height) throws WriterException, IOException {
        // 图片的格式
        String format = "jpg";
        // 定义二维码的参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 定义字符集编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 纠错的等级 L > M > Q > H 纠错的能力越高可存储的越少，一般使用M
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置图片边距
        hints.put(EncodeHintType.MARGIN, 2);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig();
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
        //新建流。
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        ImageIO.write(image, format, os);
        //从流中获取数据数组。
        byte[] b = os.toByteArray();
        String str = Base64.getEncoder().encodeToString(b);
        return "data:image/jpg;base64," + str;
    }
}
