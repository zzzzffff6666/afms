package com.bjtu.afms.web.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PlanImportParam {
    @NotNull
    private Integer poolCycleId;
    @NotNull
    private Integer poolId;
    @NotNull
    private Integer cycle;
    @NotNull
    private String name;
}
