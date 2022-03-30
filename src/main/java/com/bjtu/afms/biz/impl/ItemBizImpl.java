package com.bjtu.afms.biz.impl;

import com.alibaba.fastjson.JSON;
import com.bjtu.afms.biz.ItemBiz;
import com.bjtu.afms.biz.LogBiz;
import com.bjtu.afms.biz.PermissionBiz;
import com.bjtu.afms.config.context.LoginContext;
import com.bjtu.afms.config.handler.Assert;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
import com.bjtu.afms.enums.OperationType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Page;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.model.Log;
import com.bjtu.afms.service.ItemService;
import com.bjtu.afms.service.LogService;
import com.bjtu.afms.utils.ConfigUtil;
import com.bjtu.afms.utils.DateUtil;
import com.bjtu.afms.web.param.query.ItemQueryParam;
import com.bjtu.afms.web.param.query.LogQueryParam;
import com.bjtu.afms.web.vo.ItemTokenVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private LogService logService;

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
    public Page<ItemTokenVO> getMyTakeLog(Integer page) {
        if (page == null) {
            page = 0;
        }
        LogQueryParam param = new LogQueryParam();
        param.setOrderBy("add_time desc");
        param.setUserId(LoginContext.getUserId());
        param.setOperationId(OperationType.TAKE_ITEM.getId());
        PageHelper.startPage(page, configUtil.getPageSize());
        PageInfo<Log> pageInfo = new PageInfo<>(logService.selectLogList(param));
        List<ItemTokenVO> itemTokenVOList = pageInfo.getList().stream()
                .map(log -> {
                    Item oldItem = JSON.parseObject(log.getOldValue(), Item.class);
                    Item newItem = JSON.parseObject(log.getNewValue(), Item.class);
                    return new ItemTokenVO(log.getId(), oldItem.getName(),
                            oldItem.getAmount() - newItem.getAmount(), log.getAddTime());
                })
                .collect(Collectors.toList());
        return new Page<>(pageInfo, itemTokenVOList);
    }

    @Override
    @Transactional
    public boolean statusChange(int itemId, ItemType itemType, ItemStatus newStatus) {
        Item item = itemService.selectItem(itemId);
        Assert.notNull(item, APIError.NOT_FOUND);
        Assert.isTrue(itemType == null || item.getType() == itemType.getId(), APIError.ITEM_TYPE_ERROR);
        Assert.isTrue(ItemStatus.changeCheck(item.getStatus(), newStatus), APIError.ITEM_STATUS_CHANGE_ERROR);

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
    }

    @Override
    @Transactional
    public boolean takeItem(int itemId, ItemType itemType, int amount) {
        Item item = itemService.selectItem(itemId);
        Assert.notNull(item, APIError.NOT_FOUND);
        Assert.isTrue(item.getType() == itemType.getId(), APIError.ITEM_TYPE_ERROR);
        Assert.isTrue(item.getStatus() == ItemStatus.ACTIVE.getId(), APIError.ITEM_CANNOT_USE);
        Assert.isTrue(item.getAmount() >= amount, APIError.ITEM_AMOUNT_NOT_ENOUGH);

        Item record = new Item();
        record.setId(itemId);
        record.setAmount(item.getAmount() - amount);
        if (record.getAmount() == 0) {
            record.setStatus(ItemStatus.DEPLETED.getId());
        }
        if (itemService.updateItem(record) == 1) {
            logBiz.saveLog(DataType.ITEM, itemId, OperationType.TAKE_ITEM,
                    JSON.toJSONString(item), JSON.toJSONString(record));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean returnItem(int itemId, int amount) {
        Item item = itemService.selectItem(itemId);
        Assert.notNull(item, APIError.NOT_FOUND);
        Item record = new Item();
        record.setId(itemId);
        record.setAmount(item.getAmount() + amount);
        if (item.getStatus() == ItemStatus.DEPLETED.getId()) {
            record.setStatus(ItemStatus.ACTIVE.getId());
        }
        if (itemService.updateItem(record) == 1) {
            logBiz.saveLog(DataType.ITEM, itemId, OperationType.RETURN_ITEM,
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
        Assert.notNull(old, APIError.NOT_FOUND);
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
        Assert.notNull(old, APIError.NOT_FOUND);
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
