package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.ClientBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.service.ClientService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.web.param.query.ClientQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Component
public class ClientBizImpl implements ClientBiz {

    @Resource
    private ClientService clientService;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private PermissionBiz permissionBiz;

    @Override
    public Page<Client> getClientList(ClientQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Client> pageInfo = new PageInfo<>(clientService.selectClientByContent(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertClient(Client client) {
        if (clientService.insertClient(client) == 1) {
            return permissionBiz.initResourceOwner(DataType.CLIENT.getId(), client.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteClient(int clientId) {
        permissionBiz.deleteResourceOwner(DataType.CLIENT.getId(), clientId);
        return clientService.deleteClient(clientId) == 1;
    }
}
