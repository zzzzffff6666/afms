package com.bjtu.afms.controller;

import com.bjtu.afms.enums.*;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.utils.ListUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enum")
public class EnumController {

    @GetMapping("/authType")
    public Result getAuthType() {
        return Result.ok(ListUtil.newArrayList(AuthType.values()));
    }

    @GetMapping("/dataType")
    public Result getDataType() {
        return Result.ok(ListUtil.newArrayList(DataType.values()));
    }

    @GetMapping("/fundType")
    public Result getFundType() {
        return Result.ok(ListUtil.newArrayList(FundType.values()));
    }

    @GetMapping("/itemStatus")
    public Result getItemStatus() {
        return Result.ok(ListUtil.newArrayList(ItemStatus.values()));
    }

    @GetMapping("/itemType")
    public Result getItemType() {
        return Result.ok(ListUtil.newArrayList(ItemType.values()));
    }

    @GetMapping("/operationType")
    public Result getOperationType() {
        return Result.ok(ListUtil.newArrayList(OperationType.values()));
    }

    @GetMapping("/planFinish")
    public Result getPlanFinish() {
        return Result.ok(ListUtil.newArrayList(PlanFinish.values()));
    }

    @GetMapping("/poolStatus")
    public Result getPoolStatus() {
        return Result.ok(ListUtil.newArrayList(PoolStatus.values()));
    }

    @GetMapping("/poolType")
    public Result getPoolType() {
        return Result.ok(ListUtil.newArrayList(PoolType.values()));
    }

    @GetMapping("/cycleStatus")
    public Result getCycleStatus() {
        return Result.ok(TaskStatus.getCycleStatus());
    }

    @GetMapping("/alertStatus")
    public Result getAlertStatus() {
        return Result.ok(TaskStatus.getCycleStatus());
    }

    @GetMapping("/taskStatus")
    public Result getTaskStatus() {
        return Result.ok(ListUtil.newArrayList(TaskStatus.values()));
    }

    @GetMapping("/urgentLevel")
    public Result getUrgentLevel() {
        return Result.ok(ListUtil.newArrayList(UrgentLevel.values()));
    }

    @GetMapping("/userStatus")
    public Result getUserStatus() {
        return Result.ok(ListUtil.newArrayList(UserStatus.values()));
    }

    @GetMapping("/verifyStatus")
    public Result getVerifyStatus() {
        return Result.ok(ListUtil.newArrayList(VerifyStatus.values()));
    }
}
