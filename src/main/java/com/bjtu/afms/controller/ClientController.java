package com.bjtu.afms.controller;

import com.bjtu.afms.biz.ClientBiz;
import com.bjtu.afms.config.annotation.AuthCheck;
import com.bjtu.afms.enums.AuthType;
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

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @GetMapping("/admin/client/info/{clientId}")
    public Result getClientInfo(@PathVariable("clientId") int clientId) {
        return Result.ok(clientService.selectClient(clientId));
    }

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @GetMapping({"/admin/client/all", "/admin/client/all/{page}"})
    public Result getAllClient(@RequestParam ClientQueryParam param,
                               @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(clientBiz.getClientList(param, page));
    }

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @GetMapping({"/admin/client/list", "/admin/client/list/{page}"})
    public Result getClientListByContent(@RequestParam ClientQueryParam param,
                                         @PathVariable(value = "page", required = false) Integer page) {
        return Result.ok(clientBiz.getClientList(param, page));
    }

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @PostMapping("/admin/client/insert")
    public Result addClient(@RequestBody Client client) {
        if (clientService.insertClient(client) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.INSERT_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @PostMapping("/admin/client/info/modify")
    public Result modifyClientInfo(@RequestBody Client client) {
        client.setAddTime(null);
        client.setAddUser(null);
        if (clientService.updateClient(client) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.UPDATE_ERROR);
        }
    }

    @AuthCheck(auth = AuthType.CLIENT_CONTACT)
    @PostMapping("/admin/client/delete/{clientId}")
    public Result deleteClient(@PathVariable("clientId") int clientId) {
        if (clientService.deleteClient(clientId) == 1) {
            return Result.ok();
        } else {
            return Result.error(APIError.DELETE_ERROR);
        }
    }
}
