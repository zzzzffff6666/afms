package com.bjtu.afms.utils;

import javax.validation.constraints.NotNull;
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

    public static <T> List<T> copyElement(T element, int times) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < times; ++i) {
            result.add(element);
        }
        return result;
    }

}
