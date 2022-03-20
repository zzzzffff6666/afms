package com.bjtu.afms.utils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class SetUtil {

    public static <T> Set<T> newHashSet(@NotNull T... items) {
        Set<T> result = new HashSet<>();
        for (T item : items) {
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }
}
