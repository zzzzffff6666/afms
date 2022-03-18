package com.bjtu.afms.controller;

import com.bjtu.afms.biz.ItemBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.enums.ItemStatus;
import com.bjtu.afms.enums.ItemType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Item;
import com.bjtu.afms.service.ItemService;
import com.bjtu.afms.web.param.query.ItemQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ItemController {

    @Resource
    private ItemService itemService;

    @Resource
    private ItemBiz itemBiz;

    @GetMapping("/item/info/{itemId}")
    public Result getItemInfo(@PathVariable("itemId") int id) {
        Item item = itemService.selectItem(id);
        if (item != null) {
            return Result.ok(item);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/item/all", "/item/all/{page}"})
    public Result getAllItem(ItemQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(itemBiz.getItemList(param, page));
    }

    @GetMapping({"/item/list", "/item/list/{page}"})
    public Result getItemList(ItemQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(itemBiz.getItemList(param, page));
    }

    @PostMapping("/item/tool/lent/{itemId}")
    public Result toolLent(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.TOOL, ItemStatus.LENT)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/tool/broken/{itemId}")
    public Result toolBroken(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.TOOL, ItemStatus.BROKEN)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/tool/upkeep/{itemId}")
    public Result toolUpkeep(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.TOOL, ItemStatus.UPKEEP)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/tool/active/{itemId}")
    public Result toolActive(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.TOOL, ItemStatus.ACTIVE)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/feed/take/{itemId}")
    public Result feedTake(@PathVariable("itemId") int id, @RequestParam("amount") int amount) {
        if (itemBiz.takeFeedOrMedicine(id, ItemType.FEED, amount)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/feed/expire/{itemId}")
    public Result feedExpire(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.FEED, ItemStatus.EXPIRED)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/feed/deplete/{itemId}")
    public Result feedDeplete(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.FEED, ItemStatus.DEPLETED)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/medicine/take/{itemId}")
    public Result medicineTake(@PathVariable("itemId") int id, @RequestParam("amount") int amount) {
        if (itemBiz.takeFeedOrMedicine(id, ItemType.MEDICINE, amount)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/medicine/expire/{itemId}")
    public Result medicineExpire(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.MEDICINE, ItemStatus.EXPIRED)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @PostMapping("/item/medicine/deplete/{itemId}")
    public Result medicineDeplete(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, ItemType.MEDICINE, ItemStatus.DEPLETED)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STORE_MANAGER}, owner = true, data = DataType.ITEM)
    @PostMapping("/admin/item/disuse/{itemId}")
    public Result itemDisuse(@PathVariable("itemId") int id) {
        if (itemBiz.statusChange(id, null, ItemStatus.DISUSED)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STORE_MANAGER})
    @PostMapping("/admin/item/insert")
    public Result addItem(@RequestBody @Validated Item item) {
        if (itemBiz.insertItem(item)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STORE_MANAGER}, owner = true, data = DataType.ITEM)
    @PostMapping("/admin/item/info/modify")
    public Result modifyItemInfo(@RequestBody @Validated Item item) {
        item.setAddTime(null);
        item.setAddUser(null);
        if (itemService.updateItem(item) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.STORE_MANAGER}, owner = true, data = DataType.ITEM)
    @PostMapping("/admin/item/delete/{itemId}")
    public Result deleteItem(@PathVariable("itemId") int id) {
        if (itemBiz.deleteItem(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
