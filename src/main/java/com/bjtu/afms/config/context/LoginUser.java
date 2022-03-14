package com.bjtu.afms.config.context;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private Integer id;
    private String name;
    private String phone;
}
