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
    public Result getStoreInfo(@PathVariable("storeId") int storeId) {
        return Result.ok(storeService.selectStore(storeId));
    }

    @GetMapping({"/store/all", "/store/all/{page}"})
    public Result getAllStore(@RequestParam StoreQueryParam param,
                              @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(storeBiz.getStoreList(param, page));
    }

    @GetMapping({"/store/list", "/store/list/{page}"})
    public Result getStoreList(@RequestParam StoreQueryParam param,
                              @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(storeBiz.getStoreList(param, page));
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN})
    @PostMapping("/admin/store/insert")
    public Result addStore(@RequestBody Store store) {
        if (storeBiz.insertStore(store)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN}, owner = true, data = DataType.STORE)
    @PostMapping("/admin/store/manager/modify")
    public Result modifyStoreManager(@RequestParam("id") int id, @RequestParam("manager") int manager) {
        Store store = new Store();
        store.setId(id);
        store.setManager(manager);
        if (storeService.updateStore(store) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN}, owner = true, data = DataType.STORE)
    @PostMapping("/admin/store/info/modify")
    public Result modifyStoreManager(@RequestParam("id") int id, @RequestParam("name") String name,
                                     @RequestParam("url") String url) {
        Store store = new Store();
        store.setId(id);
        store.setName(name);
        store.setUrl(url);
        if (storeService.updateStore(store) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.STORE_MANAGER, AuthType.ADMIN})
    @PostMapping("/admin/store/delete/{storeId}")
    public Result deleteStore(@PathVariable("storeId") int storeId) {
        if (storeService.deleteStore(storeId) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
