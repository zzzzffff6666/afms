package com.bjtu.afms.web.param;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class LoginParam {
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;
    private String credential;
}
