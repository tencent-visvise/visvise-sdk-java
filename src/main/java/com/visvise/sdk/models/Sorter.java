package com.visvise.sdk.models;

/**
 * Sorter represents the sorting rule for get_model_list.
 */
public class Sorter {
    private String name;   // 排序字段 (e.g. create_time)
    private String order;  // 排序顺序 (asc/desc)

    public Sorter() {
    }

    public Sorter(String name, String order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
