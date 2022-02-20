package com.bjtu.afms.controller;

import com.bjtu.afms.http.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/login")
    public Result login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        if (phone.equals("17770517023") && password.equals("123")) {
            return Result.ok();
        }
        return Result.error("出错了");
    }
}
