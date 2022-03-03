package com.bjtu.afms.config.annotation;

import com.bjtu.afms.enums.AuthType;
import com.bjtu.afms.enums.DataType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AuthCheck {

    AuthType[] auth();

    boolean owner() default false;

    DataType data() default DataType.USER;
}
