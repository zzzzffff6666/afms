package com.bjtu.afms.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConfigUtil {
    @Value("${afms.code-length}")
    private int codeLength;
    @Value("${afms.salt-length}")
    private int saltLength;
    @Value("${afms.hash-times}")
    private int hashTimes;
    @Value("${afms.page-size}")
    private int pageSize;
}
