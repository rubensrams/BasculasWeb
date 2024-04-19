package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.MaterialRepository;
import com.materiales.bascula.model.Detalle;
import com.materiales.bascula.model.Material;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public List<Detalle> find(String idCompra) {
        return materialRepository.find(idCompra);
    }

    public boolean update(int idMaterial, double precio) {
        return materialRepository.save(idMaterial, precio);
    }

    public boolean insert(String nombre, double precio) {
        return materialRepository.save(nombre, precio);
    }

}
