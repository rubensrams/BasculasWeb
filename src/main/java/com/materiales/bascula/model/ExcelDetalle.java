package com.materiales.bascula.model;

public class ExcelDetalle {
    
    private String recuperador;
    private int folio;
    private String usuario;
    private String sucursal;
    private double total;
    private String fecha;
    private String code;
    private double pesoBruto;
    private double pesoNeto;
    private double tara;
    private double precio;
    private double subtotal;
    private String flete;

    public String getRecuperador() {
        return recuperador;
    }
    public void setRecuperador(String recuperador) {
        this.recuperador = recuperador;
    }
    public int getFolio() {
        return folio;
    }
    public void setFolio(int folio) {
        this.folio = folio;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSucursal() {
        return sucursal;
    }
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public double getPesoBruto() {
        return pesoBruto;
    }
    public void setPesoBruto(double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }
    public double getPesoNeto() {
        return pesoNeto;
    }
    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }
    public double getTara() {
        return tara;
    }
    public void setTara(double tara) {
        this.tara = tara;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public String getFlete() {
        return flete;
    }

    public void setFlete(String flete) {
        this.flete = flete;
    }
    
    @Override
    public String toString() {
        return "ExcelDetalle [recuperador=" + recuperador + ", folio=" + folio + ", usuario=" + usuario + ", sucursal="
                + sucursal + ", total=" + total + ", fecha=" + fecha + ", code=" + code + ", pesoBruto=" + pesoBruto
                + ", pesoNeto=" + pesoNeto + ", tara=" + tara + ", precio=" + precio + ", subtotal=" + subtotal + "]";
    }
    
}
