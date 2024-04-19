package com.materiales.bascula.model;

public class Recuperador {

    private int idRecuperador;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String email;
    private String telefono;

    public int getIdRecuperador() {
        return idRecuperador;
    }

    public void setIdRecuperador(int idRecuperador) {
        this.idRecuperador = idRecuperador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Recuperador [idRecuperador=" + idRecuperador + ", nombre=" + nombre + ", apPaterno=" + apPaterno
                + ", apMaterno=" + apMaterno + ", email=" + email + ", telefono="
                + telefono + "]";
    }

}
