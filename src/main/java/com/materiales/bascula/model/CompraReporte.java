package com.materiales.bascula.model;

import java.util.Date;

public class CompraReporte {

    private int idCompra;
    private int folio;
    private Date fecha;
    private double total;
    private String usuario;
    private String recuperador;
    private String flete;
    private String status;
    private String sucursal;
    private String material;
    private double pesoNeto;
    private double precio;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRecuperador() {
        return recuperador;
    }

    public void setRecuperador(String recuperador) {
        this.recuperador = recuperador;
    }

    public String getFlete() {
        return flete;
    }

    public void setFlete(String flete) {
        this.flete = flete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
