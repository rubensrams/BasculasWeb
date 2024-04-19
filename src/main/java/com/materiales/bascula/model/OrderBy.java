package com.materiales.bascula.model;

public enum OrderBy {

    NO_ORDER(""),
    NAME("ORDER BY name"),
    NAME_DESC("ORDER BY name DESC"),
    USERNAME("ORDER BY username"),
    USERNAME_DESC("ORDER BY username DESC"),
    NOMBRE("ORDER BY nombre"),
    NOMBRE_DESC("ORDER BY nombre DESC");

    private String order;

    OrderBy(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
