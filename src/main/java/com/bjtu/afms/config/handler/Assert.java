package com.bjtu.afms.config.handler;

import com.bjtu.afms.exception.BizException;

import java.util.Collection;

public class Assert {

    public static void isTrue(boolean exp, String errorMsg) throws BizException {
        if (!exp) {
            throw new BizException(errorMsg);
        }
    }

    public static void notNull(Object obj, String errorMsg) throws BizException {
        if (obj == null) {
            throw new BizException(errorMsg);
        }
    }

    public static void isNull(Object obj, String errorMsg) throws BizException {
        if (obj != null) {
            throw new BizException(errorMsg);
        }
    }

    public static void notEmpty(Collection<?> collection, String errorMsg) throws BizException {
        if (collection == null || collection.isEmpty()) {
            throw new BizException(errorMsg);
        }
    }

    public static void isEmpty(Collection<?> collection, String errorMsg) throws BizException {
        if (collection != null && !collection.isEmpty()) {
            throw new BizException(errorMsg);
        }
    }
}
