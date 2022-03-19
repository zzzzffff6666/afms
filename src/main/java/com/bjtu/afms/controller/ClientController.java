package com.bjtu.afms.controller;

import com.bjtu.afms.biz.ClientBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;
import com.bjtu.afms.http.APIError;
import com.bjtu.afms.http.Result;
import com.bjtu.afms.model.Client;
import com.bjtu.afms.service.ClientService;
import com.bjtu.afms.web.param.query.ClientQueryParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ClientController {

    @Resource
    private ClientService clientService;

    @Resource
    private ClientBiz clientBiz;

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN})
    @GetMapping("/client/info/{clientId}")
    public Result getClientInfo(@PathVariable("clientId") int id) {
        Client client = clientService.selectClient(id);
        if (client != null) {
            return Result.ok(client);
        } else {
            return Result.error(APIError.NOT_FOUND);
        }
    }

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN})
    @GetMapping({"/client/all", "/client/all/{page}"})
    public Result getAllClient(ClientQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(clientBiz.getClientList(param, page));
    }

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN})
    @GetMapping({"/client/list", "/client/list/{page}"})
    public Result getClientListByContent(ClientQueryParam param, @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(clientBiz.getClientList(param, page));
    }

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN})
    @PostMapping("/client/insert")
    public Result addClient(@RequestBody Client client) {
        if (clientBiz.insertClient(client)) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN}, owner = true, data = DataType.CLIENT)
    @PostMapping("/client/info/modify")
    public Result modifyClientInfo(@RequestBody Client client) {
        client.setAddTime(null);
        client.setAddUser(null);
        if (clientService.updateClient(client) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = {AuthType.CLIENT_CONTACT, AuthType.ADMIN}, owner = true, data = DataType.CLIENT)
    @PostMapping("/client/delete/{clientId}")
    public Result deleteClient(@PathVariable("clientId") int id) {
        if (clientBiz.deleteClient(id)) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
