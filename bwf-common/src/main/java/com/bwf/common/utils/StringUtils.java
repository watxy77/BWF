package com.bwf.common.utils;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

public class StringUtils {
    private static final String[] EMPTY_STRING_ARRAY = {};
    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
