package com.bjtu.afms.controller;

import com.bjtu.afms.biz.FundBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Fund;
import com.bjtu.afms.service.FundService;
import com.bjtu.afms.web.param.query.FundQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class FundController {

    @Resource
    private FundService fundService;

    @Resource
    private FundBiz fundBiz;

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER}, owner = true, data = DataType.FUND)
    @GetMapping("/fund/info/{fundId}")
    public Result getFundInfo(@PathVariable("fundId") int id) {
        Fund fund = fundService.selectFund(id);
        if (fund != null) {
            return Result.ok(fund);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @GetMapping({"/my/fund/list", "/my/fund/list/{page}"})
    public Result getMyFundList(@PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(fundBiz.getMyFundList(page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER})
    @GetMapping({"/fund/all", "/fund/all/{page}"})
    public Result getAllFund(FundQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(fundBiz.getFundList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER})
    @GetMapping({"/fund/list", "/fund/list/{page}"})
    public Result getFundList(FundQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(fundBiz.getFundList(param, page));
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER})
    @PostMapping("/fund/insert")
    public Result addFund(@RequestBody Fund fund) {
        if (fundBiz.insertFund(fund)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER}, owner = true, data = DataType.FUND)
    @PostMapping("/fund/info/modify")
    public Result modifyFundInfo(@RequestBody Fund fund) {
        if (fundBiz.modifyFundInfo(fund)) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.ADMIN, AuthType.FUND_REVIEWER}, owner = true, data = DataType.FUND)
    @PostMapping("/fund/delete/{fundId}")
    public Result deleteFund(@PathVariable("fundId") int id) {
        if (fundBiz.deleteFund(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
