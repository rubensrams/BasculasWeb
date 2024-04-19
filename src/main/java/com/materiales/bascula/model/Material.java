package com.materiales.bascula.model;

public class Material {

    private int idMaterial;
    private String code;
    private String description;
    private double price;

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

}
