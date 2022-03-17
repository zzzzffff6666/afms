package com.bjtu.afms.web.param;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ModifyCycleFundParam {
    private Integer id;
    private BigDecimal cost;
    private BigDecimal income;
}
