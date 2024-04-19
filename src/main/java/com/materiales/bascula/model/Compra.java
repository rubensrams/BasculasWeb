package com.materiales.bascula.model;

import java.util.List;

public class Compra {

    private int idUsuario;
    private int idRecuperador;
    private int idSucursal;
    private int idFlete;
    private double total;
    private List<Detalle> detalle;

    public int getIdRecuperador() {
        return idRecuperador;
    }

    public void setIdRecuperador(int idRecuperador) {
        this.idRecuperador = idRecuperador;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Detalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        this.detalle = detalle;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdFlete() {
        return idFlete;
    }

    public void setIdFlete(int idFlete) {
        this.idFlete = idFlete;
    }

    @Override
    public String toString() {
        return "Compra [idUsuario=" + idUsuario + ", idRecuperador=" + idRecuperador + ", idSucursal=" + idSucursal
                + ", idFlete=" + idFlete + ", total=" + total + ", detalle=" + detalle + "]";
    }

}
