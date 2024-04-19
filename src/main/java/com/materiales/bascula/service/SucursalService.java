package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.database.SucursalRepository;
import com.materiales.bascula.model.Sucursal;

@Repository
public class SucursalService {
    
    @Autowired
    SucursalRepository sucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public boolean update(int idSucursal, String nombre) {
        return sucursalRepository.update(idSucursal, nombre);
    }

    public boolean insert(String nombre) {
        return sucursalRepository.insert(nombre);
    }
}
