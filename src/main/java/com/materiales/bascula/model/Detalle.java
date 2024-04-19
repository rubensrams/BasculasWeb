package com.materiales.bascula.model;

public class Detalle {

    private int idProducto;
    private String nombreProducto;
    private double pesoNeto;
    private double pesoBruto;
    private double tara;
    private double precioNegociado;
    private double total;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public double getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public double getTara() {
        return tara;
    }

    public void setTara(double tara) {
        this.tara = tara;
    }

    public double getPrecioNegociado() {
        return precioNegociado;
    }

    public void setPrecioNegociado(double precioNegociado) {
        this.precioNegociado = precioNegociado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    @Override
    public String toString() {
        return "Detalle [idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", pesoNeto=" + pesoNeto
                + ", pesoBruto=" + pesoBruto + ", tara=" + tara + ", precioNegociado=" + precioNegociado + ", total="
                + total + "]";
    }

}