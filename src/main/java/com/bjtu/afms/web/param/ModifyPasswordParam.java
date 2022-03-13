package com.bjtu.afms.web.param;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ModifyPasswordParam {
    private String passwordNew;

    private String credential;

    @NotNull
    @Max(2)
    @Min(1)
    private int type;
}
