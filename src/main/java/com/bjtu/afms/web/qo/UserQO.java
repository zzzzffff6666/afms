package com.bjtu.afms.web.qo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserQO {
    private Integer id;
    private String name;
    private String phone;
}
