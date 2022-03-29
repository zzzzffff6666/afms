package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.ItemBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.exception.BizException;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.service.ItemService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.DateUtil;
import com.bjtu.afms.web.param.query.ItemQueryParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class ItemBizImpl implements ItemBiz {

    @Resource
    private ItemService itemService;

    @Resource
    private PermissionBiz permissionBiz;

    @Resource
    private ConfigUtil configUtil;

    @Resource
    private LogBiz logBiz;

    @Override
    public Page<Item> getItemList(ItemQueryParam param, Integer page) {
        if (page == null) {
            page = 0;
        }
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Item> pageInfo = new PageInfo<>(itemService.selectItemList(param));
        return new Page<>(pageInfo);
    }

    @Override
    @Transactional
    public boolean statusChange(int itemId, ItemType itemType, ItemStatus newStatus) {
        Item item = itemService.selectItem(itemId);
        if (item == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (itemType != null && item.getType() != itemType.getId()) {
            throw new BizException(APIError.ITEM_TYPE_ERROR);
        }
        if (ItemStatus.changeCheck(item.getStatus(), newStatus)) {
            Item record = new Item();
            record.setId(itemId);
            record.setStatus(newStatus.getId());
            if (itemType == ItemType.TOOL && item.getStatus() == ItemStatus.UPKEEP.getId()) {
                int maintainInterval = item.getMaintainInterval() == null ?
                        configUtil.getDefaultMaintainInterval() : item.getMaintainInterval();
                item.setMaintainTime(DateUtil.plusDays(maintainInterval));
            }
            if (itemService.updateItem(item) == 1) {
                logBiz.saveLog(DataType.ITEM, itemId, OperationType.UPDATE_ITEM_STATUS,
                        JSON.toJSONString(item), JSON.toJSONString(record));
                return true;
            } else {
                return false;
            }
        } else {
            throw new BizException(APIError.ITEM_STATUS_CHANGE_ERROR);
        }
    }

    @Override
    @Transactional
    public boolean takeFeedOrMedicine(int itemId, ItemType itemType, int amount) {
        Item item = itemService.selectItem(itemId);
        if (item == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (item.getType() != itemType.getId()) {
            throw new BizException(APIError.ITEM_TYPE_ERROR);
        }
        if (item.getStatus() != ItemStatus.ACTIVE.getId()) {
            throw new BizException(APIError.ITEM_CANNOT_USE);
        }
        Item record = new Item();
        if (item.getAmount() > amount) {
            record.setId(itemId);
            record.setAmount(item.getAmount() - amount);
        } else if (item.getAmount() == amount) {
            record.setId(itemId);
            record.setAmount(0);
            record.setStatus(ItemStatus.DEPLETED.getId());
        } else {
            throw new BizException(APIError.ITEM_AMOUNT_NOT_ENOUGH);
        }
        if (itemService.updateItem(record) == 1) {
            logBiz.saveLog(DataType.ITEM, itemId, OperationType.UPDATE_ITEM_STATUS,
                    JSON.toJSONString(item), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean insertItem(Item item) {
        item.setStatus(ItemStatus.ACTIVE.getId());
        item.setModTime(null);
        item.setModUser(null);
        if (itemService.insertItem(item) == 1) {
            permissionBiz.initResourceOwner(DataType.ITEM.getId(), item.getId(), LoginContext.getUserId());
            logBiz.saveLog(DataType.ITEM, item.getId(), OperationType.INSERT_ITEM,
                    null, JSON.toJSONString(item));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean modifyItemInfo(Item item) {
        Item old = itemService.selectItem(item.getId());
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        item.setAddTime(null);
        item.setAddUser(null);
        item.setStatus(null);
        if (itemService.updateItem(item) == 1) {
            logBiz.saveLog(DataType.ITEM, item.getId(), OperationType.UPDATE_ITEM_INFO,
                    JSON.toJSONString(old), JSON.toJSONString(item));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteItem(int itemId) {
        Item old = itemService.selectItem(itemId);
        if (old == null) {
            throw new BizException(APIError.NOT_FOUND);
        }
        if (itemService.deleteItem(itemId) == 1) {
            permissionBiz.deleteResource(DataType.ITEM.getId(), itemId);
            logBiz.saveLog(DataType.ITEM, itemId, OperationType.DELETE_ITEM,
                    JSON.toJSONString(old), null);
            return true;
        } else {
            return false;
        }
    }
}
