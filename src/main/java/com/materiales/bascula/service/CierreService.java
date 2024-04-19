package com.materiales.bascula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.CierreRepository;

@Service
public class CierreService {

    @Autowired
    CierreRepository cierreRepository;

    public String save(int idUser, double entrada, double salida) {
        return cierreRepository.save(idUser, entrada, salida);
    }
    
}
