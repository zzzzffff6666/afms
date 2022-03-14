package com.bjtu.afms.service;

import com.bjtu.afms.mapper.ClientMapper;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.model.ClientExample;
import com.bjtu.afms.web.param.query.ClientQueryParam;
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

    public List<Client> selectClientByContent(ClientQueryParam param) {
        ClientExample example = new ClientExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        ClientExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(param.getContent())) {
            criteria.andNameLike("%" + param.getContent() + "%");
            example.or().andPhoneLike(param.getContent() + "%");
            example.or().andNameEpLike("%" + param.getContent() + "%");
        }
        return clientMapper.selectByExample(example);
    }
}
