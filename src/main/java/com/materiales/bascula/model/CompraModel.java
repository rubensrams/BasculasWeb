package com.materiales.bascula.model;

import java.util.Date;

public class CompraModel {

    private int idCompra;
    private int folio;
    private String recuperador;
    private double total;
    private String sucursal;
    private String usuario;
    private int idEstatus;
    private Date fecha;
    private String flete;

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFlete() {
        return flete;
    }

    public void setFlete(String flete) {
        this.flete = flete;
    }

}
