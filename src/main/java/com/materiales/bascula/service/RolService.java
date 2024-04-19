package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.RolRepository;
import com.materiales.bascula.model.Rol;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
    
}
