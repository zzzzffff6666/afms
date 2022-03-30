package com.bjtu.afms.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTokenVO {
    private Integer id;
    private String name;
    private Integer amount;
    private Date time;
}
