package com.monkey.framework.entity;


/**
 * Description 键值对
 *
 * @author monkey
 * @email j_monkey@sina.cn
 * @Created at 2016/9/6
 */
public class KeyValue implements Comparable<KeyValue> {
    private String key;
    private String value;

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public int compareTo(KeyValue another) {
        return this.getKey().compareTo(another.getKey());
    }
}
