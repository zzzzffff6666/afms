package com.bjtu.afms.utils;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> List<T> newArrayList(@NotNull T... items) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

}
