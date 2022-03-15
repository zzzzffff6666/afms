package com.bjtu.afms.controller;

import com.bjtu.afms.biz.ItemBiz;
import com.bjtu.afms.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ItemController {

    @Resource
    private ItemService itemService;

    @Resource
    private ItemBiz itemBiz;


}
