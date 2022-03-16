package com.bjtu.afms.cron;

import com.bjtu.afms.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class ItemStatusTask {

    @Resource
    private ItemService itemService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void correctItemStatus() {
        // 过期，数量不够修改为耗尽，维修时间到提醒维修
    }
}
