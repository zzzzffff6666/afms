package com.bjtu.afms.biz.impl;

import com.bjtu.afms.biz.ItemBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
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
            return itemService.updateItem(item) == 1;
        } else {
            throw new BizException(APIError.ITEM_STATUS_CHANGE_ERROR);
        }
    }

    @Override
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
        if (item.getAmount() > amount) {
            Item record = new Item();
            record.setId(itemId);
            record.setAmount(item.getAmount() - amount);
            return itemService.updateItem(record) == 1;
        } else if (item.getAmount() == amount) {
            Item record = new Item();
            record.setId(itemId);
            record.setAmount(0);
            record.setStatus(ItemStatus.DEPLETED.getId());
            return itemService.updateItem(record) == 1;
        } else {
            throw new BizException(APIError.ITEM_AMOUNT_NOT_ENOUGH);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertItem(Item item) {
        if (itemService.insertItem(item) == 1) {
            return permissionBiz.initResourceOwner(DataType.ITEM.getId(), item.getId(), LoginContext.getUserId());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteItem(int itemId) {
        permissionBiz.deleteResourceOwner(DataType.ITEM.getId(), itemId);
        return itemService.deleteItem(itemId) == 1;
    }
}
