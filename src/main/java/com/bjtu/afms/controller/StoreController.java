package com.bjtu.afms.controller;

import com.bjtu.afms.biz.StoreBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Store;
import com.bjtu.afms.service.StoreService;
import com.bjtu.afms.web.param.query.StoreQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class StoreController {

    @Resource
    private StoreService storeService;

    @Resource
    private StoreBiz storeBiz;

    @GetMapping("/store/info/{storeId}")
    public Result getStoreInfo(@PathVariable("storeId") int id) {
        Store store = storeService.selectStore(id);
        if (store != null) {
            return Result.ok(store);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/store/all", "/store/all/{page}"})
    public Result getAllStore(StoreQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(storeBiz.getStoreList(param, page));
    }

    @GetMapping({"/store/list", "/store/list/{page}"})
    public Result getStoreList(StoreQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(storeBiz.getStoreList(param, page));
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN})
    @PostMapping("/store/insert")
    public Result addStore(@RequestBody Store store) {
        if (storeBiz.insertStore(store)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN}, owner = true, data = DataType.STORE)
    @PostMapping("/store/manager/modify")
    public Result modifyStoreManager(@RequestBody Store store) {
        Store record = new Store();
        record.setId(store.getId());
        record.setManager(store.getManager());
        if (storeService.updateStore(record) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN}, owner = true, data = DataType.STORE)
    @PostMapping("/store/info/modify")
    public Result modifyStoreInfo(@RequestBody Store store) {
        Store record = new Store();
        record.setId(store.getId());
        record.setName(store.getName());
        record.setUrl(store.getUrl());
        if (storeService.updateStore(record) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN}, owner = true, data = DataType.CLIENT)
    @PostMapping("/store/delete/{storeId}")
    public Result deleteStore(@PathVariable("storeId") int id) {
        if (storeBiz.deleteStore(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
