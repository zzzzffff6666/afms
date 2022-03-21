package com.bjtu.afms.config.annotation;

import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuthCheck {

    AuthType[] auth() default {};

    boolean owner() default false;

    DataType data() default DataType.USER;

    boolean relate() default false;
}
