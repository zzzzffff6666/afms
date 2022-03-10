package com.bjtu.afms.service;

import com.bjtu.afms.http.Result;
import com.bjtu.afms.utils.ConfigUtil;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;

@SpringBootTest
public class ToolServiceTest {

    @Resource
    private ToolService toolService;

    @Resource
    private ConfigUtil configUtil;

    @Test
    public void testGenerateQRCode() {
        String url = "/user/info/1";
        try {
            byte[] qrCode = toolService.getQRCodeImage(url, configUtil.getQrCodeWidth(), configUtil.getQrCodeHeight());
            FileImageOutputStream image = new FileImageOutputStream(new File("C:\\Users\\zhang\\Desktop\\QRCode.png"));
            image.write(qrCode, 0, qrCode.length);//将byte写入硬盘
            image.close();
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }
}
