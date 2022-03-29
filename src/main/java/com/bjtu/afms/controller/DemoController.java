package com.bjtu.afms.controller;

import com.bjtu.afms.http.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/test1")
    public Result test1() {
        return Result.ok();
    }

    @GetMapping("/test2")
    public Result test2() {
        return Result.ok(123);
    }
}
