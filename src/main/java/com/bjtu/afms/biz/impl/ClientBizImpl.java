package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.ClientBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.APIError;
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

    @Resource
    private LogBiz logBiz;

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
    @Transactional
    public boolean insertClient(Client client) {
        if (clientService.insertClient(client) == 1) {
            permissionBiz.initResourceOwner(DataType.CLIENT.getId(), client.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.CLIENT, client.getId(), OperationType.INSERT_CLIENT,
                    null, JSON.toJSONString(client));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyClientInfo(Client client) {
        Client old = clientService.selectClient(client.getId());
        Assert.notNull(old, APIError.NOT_FOUND);
        client.setAddTime(null);
        client.setAddUser(null);
        if (clientService.updateClient(client) == 1) {
            logBiz.saveLog(DataType.CLIENT, client.getId(), OperationType.UPDATE_CLIENT_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(client));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteClient(int clientId) {
        Client old = clientService.selectClient(clientId);
        Assert.notNull(old, APIError.NOT_FOUND);
        if (clientService.deleteClient(clientId) == 1) {
            permissionBiz.deleteResource(DataType.CLIENT.getId(), clientId);
            logBiz.saveLog(DataType.CLIENT, clientId, OperationType.DELETE_CLIENT,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
