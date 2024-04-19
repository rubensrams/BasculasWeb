package com.materiales.bascula.model;

import java.util.List;

public class CompraDatatable {

    private int count;
    private List<CompraReporte> compras;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CompraReporte> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraReporte> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        return "CompraDatatable [count=" + count + ", compras=" + compras + "]";
    }

}
