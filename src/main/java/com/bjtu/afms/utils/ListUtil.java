package com.bjtu.afms.utils;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;

public class ListUtil {

    public static <T> List<T> newArrayList(@NotNull T... items) {
        return Arrays.asList(items);
    }

}
