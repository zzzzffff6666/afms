package com.bjtu.afms.web.param;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ModifyPhoneParam {
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phoneNew;

    private String codeNew;

    private String codeOld;
}
