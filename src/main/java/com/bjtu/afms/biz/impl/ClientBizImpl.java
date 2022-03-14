package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.ClientBiz;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.service.ClientService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.ClientQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClientBizImpl implements ClientBiz {

    @Resource
    private ClientService clientService;

    @Resource
    private ConfigUtil configUtil;

    @Override
    public Page<Client> getClientList(ClientQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Client> pageInfo = new PageInfo<>(clientService.selectClientByContent(param));
        return new Page<>(pageInfo);
    }
}
