package com.bjtu.afms.annotation;

import com.bjtu.afms.enums.PermissionType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PermissionCheck {

    PermissionType[] permission();

    boolean owner() default false;

}
