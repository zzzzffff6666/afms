package com.bjtu.afms.controller;

import com.bjtu.afms.biz.PoolTaskBiz;
import com.bjtu.afms.service.PoolTaskService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PoolTaskController {

    @Resource
    private PoolTaskService poolTaskService;

    @Resource
    private PoolTaskBiz poolTaskBiz;


}
