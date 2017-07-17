package com.monkey.framework.utils;

import java.util.HashMap;

/**
 * Description TODO
 *
 * @author monkey
 * @Date 2016/8/25
 * @Email j_monkey@sina.cn
 */

public class MyHashMap extends HashMap<String, String> {
    /**
     * @see HashMap#put(Object, Object)
     */
    @Override
    public String put(String key, String value) {
        if (value == null) {
            return null;
        }
        return super.put(key, value);
    }

}