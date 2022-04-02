package com.bjtu.afms.controller;

import com.bjtu.afms.biz.WorkplaceBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Workplace;
import com.bjtu.afms.service.WorkplaceService;
import com.bjtu.afms.web.param.query.WorkplaceQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class WorkplaceController {

    @Resource
    private WorkplaceService workplaceService;

    @Resource
    private WorkplaceBiz workplaceBiz;

    @GetMapping("/workplace/info/{workplaceId}")
    public Result getWorkplaceInfo(@PathVariable("workplaceId") int id) {
        Workplace workplace = workplaceService.selectWorkplace(id);
        if (workplace != null) {
            return Result.ok(workplace);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/workplace/all", "/workplace/all/{page}"})
    public Result getAllWorkplace(WorkplaceQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(workplaceBiz.getWorkplaceList(param, page));
    }

    @GetMapping({"/workplace/list", "/workplace/list/{page}"})
    public Result getWorkplaceList(WorkplaceQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(workplaceBiz.getWorkplaceList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER})
    @PostMapping("/workplace/insert")
    public Result addWorkplace(@RequestBody Workplace workplace) {
        if (workplaceBiz.insertWorkplace(workplace)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.WORKPLACE)
    @PostMapping("/workplace/info/modify")
    public Result modifyWorkplaceInfo(@RequestBody Workplace workplace) {
        if (workplaceBiz.modifyWorkplaceInfo(workplace)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.POOL_MANAGER}, owner = true, data = DataType.WORKPLACE)
    @PostMapping("/workplace/delete/{workplaceId}")
    public Result addWorkplace(@PathVariable("workplaceId") int id) {
        if (workplaceBiz.deleteWorkplace(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }
}
