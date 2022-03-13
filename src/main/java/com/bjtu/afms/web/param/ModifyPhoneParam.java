package com.bjtu.afms.web.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ModifyPhoneParam {
    @NotNull
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phoneNew;
    @NotNull
    private String codeNew;
    // 自己修改手机号时使用
    private String codeOld;
    // 管理员修改用户手机号时使用
    private Integer id;
}
