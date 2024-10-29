package com.bwf.core.beans;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PropertyValue implements Comparable<PropertyValue>,Serializable{
    private String name;
    @Nullable
    private Object value;
    private int weight;
    @Nullable
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Object getValue() {
        return value;
    }

    public void setValue(@Nullable Object value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public PropertyValue(String name, @Nullable Object value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    @Nullable
    public String getVersion() {
        return version;
    }

    public void setVersion(@Nullable String version) {
        this.version = version;
    }

    @Override
    public int compareTo(PropertyValue pv) {
        return pv.weight - this.weight;
    }

    public static void main(String[] args) {
        PropertyValue pv1 = new PropertyValue("a1", "aaa", 1);
        PropertyValue pv2 = new PropertyValue("a2", "bbb", 1);
        PropertyValue pv3 = new PropertyValue("a3", "ccc", 26);
        PropertyValue pv4 = new PropertyValue("a4", "ddd", 65);

        List<PropertyValue> alist = Arrays.asList(pv1,pv2,pv3,pv4);

        Collections.sort(alist);
        for (PropertyValue propertyValue : alist) {
            System.out.println(propertyValue.getName());
        }
    }
}
