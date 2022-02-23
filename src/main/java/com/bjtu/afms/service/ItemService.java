package com.bjtu.afms.service;

import com.bjtu.afms.mapper.ItemMapper;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.model.ItemExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Resource
    private ItemMapper itemMapper;

    public int insertItem(Item item) {
        return itemMapper.insertSelective(item);
    }

    public int deleteItem(int itemId) {
        return itemMapper.deleteByPrimaryKey(itemId);
    }

    public int updateItem(Item item) {
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    public Item selectItem(int itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    public List<Item> selectItemList(Item item, String orderByClause) {
        return selectItemList(item, null, orderByClause);
    }

    public List<Item> selectItemList(Item item, Map<String, Date> timeParam, String orderByClause) {
        ItemExample example = new ItemExample();
        if (StringUtils.isNotBlank(orderByClause)) {
            example.setOrderByClause(orderByClause);
        }
        ItemExample.Criteria criteria = example.createCriteria();
        if (item.getType() != null) {
            criteria.andTypeEqualTo(item.getType());
        }
        if (item.getStoreId() != null) {
            criteria.andStoreIdEqualTo(item.getStoreId());
        }
        if (StringUtils.isNotBlank(item.getName())) {
            criteria.andNameLike("%" + item.getName() + "%");
        }
        if (item.getStatus() != null) {
            criteria.andStatusEqualTo(item.getStatus());
        }
        if (timeParam != null) {
            Date start = timeParam.get("start");
            Date end = timeParam.get("end");
            if (end == null) {
                end = new Date();
            }
            if (timeParam.get("expire") != null) {
                criteria.andExpireTimeBetween(start, end);
            } else {
                criteria.andMaintainTimeBetween(start, end);
            }
        }
        return itemMapper.selectByExample(example);
    }
}
