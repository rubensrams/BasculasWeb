package com.materiales.bascula.model;

import java.util.List;

public class RecuperadorDatatable {

    private List<Recuperador> recuperadores;
    private int count;

    public List<Recuperador> getRecuperadores() {
        return recuperadores;
    }

    public void setRecuperadores(List<Recuperador> recuperadores) {
        this.recuperadores = recuperadores;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RecuperadorDatatable [recuperadores=" + recuperadores + ", count=" + count + "]";
    }

}
