package com.bjtu.afms.service;

import com.bjtu.afms.mapper.ClientMapper;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.model.ClientExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    public int insertClient(Client client) {
        return clientMapper.insertSelective(client);
    }

    public int deleteClient(int clientId) {
        return clientMapper.deleteByPrimaryKey(clientId);
    }

    public int updateClient(Client client) {
        return clientMapper.updateByPrimaryKeySelective(client);
    }

    public Client selectClient(int clientId) {
        return clientMapper.selectByPrimaryKey(clientId);
    }

    public List<Client> selectClientByContent(String content, String orderByClause) {
        ClientExample example = new ClientExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        if (StringUtils.isNotBlank(content)) {
            example.createCriteria().andNameLike("%" + content + "%");
            example.or().andPhoneLike(content + "%");
            example.or().andNameEpLike("%" + content + "%");
        }
        return clientMapper.selectByExample(example);
    }

    public List<Client> selectClientList(Client client, String orderByClause) {
        ClientExample example = new ClientExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        ClientExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(client.getName())) {
            criteria.andNameLike("%" + client.getName() + "%");
        }
        if (StringUtils.isNotBlank(client.getPhone())) {
            criteria.andPhoneLike(client.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(client.getNameEp())) {
            criteria.andNameEpLike("%" + client.getNameEp() + "%");
        }
        return clientMapper.selectByExample(example);
    }
}
