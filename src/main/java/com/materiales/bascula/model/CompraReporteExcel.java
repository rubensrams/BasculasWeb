package com.materiales.bascula.model;

public class CompraReporteExcel {

    private String fecha;
    private String recuperador;
    private String usuario;
    private String estatus;
    private String sucursal;
    private String pesoNeto;
    private String precio;
    private String total;
    private String folio;
    private String flete;
    private String material;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRecuperador() {
        return recuperador;
    }

    public void setRecuperador(String recuperador) {
        this.recuperador = recuperador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(String pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFlete() {
        return flete;
    }

    public void setFlete(String flete) {
        this.flete = flete;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
