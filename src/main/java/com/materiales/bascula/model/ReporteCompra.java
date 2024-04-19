package com.materiales.bascula.model;

import java.util.Date;

public class ReporteCompra {

    private int idCompra;
    private int folio;
    private String recuperador;
    private double total;
    private String sucursal;
    private String user;
    private String estatus;
    private Date fecha;

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getRecuperador() {
        return recuperador;
    }

    public void setRecuperador(String recuperador) {
        this.recuperador = recuperador;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    @Override
    public String toString() {
        return "ReporteCompra [id_compra=" + idCompra + ", folio=" + folio + ", recuperador=" + recuperador
                + ", total=" + total + ", sucursal=" + sucursal + ", user=" + user + ", estatus=" + estatus + ", fecha="
                + fecha + "]";
    }

}
