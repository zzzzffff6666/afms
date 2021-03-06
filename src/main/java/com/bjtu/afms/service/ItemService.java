package com.bjtu.afms.service;

import com.bjtu.afms.mapper.ItemMapper;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.model.ItemExample;
import com.bjtu.afms.web.param.query.ItemQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    public int updateItemStatus(int id, int status) {
        Item item = new Item();
        item.setId(id);
        item.setStatus(status);
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    public Item selectItem(int itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    public List<Item> selectItemList(ItemQueryParam param) {
        ItemExample example = new ItemExample();
        if (StringUtils.isNotBlank(param.getOrderBy())) {
            example.setOrderByClause(param.getOrderBy());
        }
        ItemExample.Criteria criteria = example.createCriteria();
        if (param.getType() != null) {
            criteria.andTypeEqualTo(param.getType());
        }
        if (param.getStoreId() != null) {
            criteria.andStoreIdEqualTo(param.getStoreId());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (param.getStatus() != null) {
            criteria.andStatusEqualTo(param.getStatus());
        }
        if (param.getExpireBegin() != null || param.getExpireLast() != null) {
            if (param.getExpireBegin() == null) {
                param.setExpireBegin(new Date(0L));
            }
            if (param.getExpireLast() == null) {
                param.setExpireLast(new Date());
            }
            criteria.andExpireTimeBetween(param.getExpireBegin(), param.getExpireLast());
        }
        if (param.getMaintainBegin() != null || param.getMaintainLast() != null) {
            if (param.getMaintainBegin() == null) {
                param.setMaintainBegin(new Date(0L));
            }
            if (param.getMaintainLast() == null) {
                param.setMaintainLast(new Date());
            }
            criteria.andExpireTimeBetween(param.getMaintainBegin(), param.getMaintainLast());
        }
        return itemMapper.selectByExample(example);
    }

    public List<Item> selectItemList(ItemExample example) {
        return itemMapper.selectByExample(example);
    }
}
