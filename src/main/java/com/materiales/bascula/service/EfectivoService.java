package com.materiales.bascula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.EfectivoRepository;

@Service
public class EfectivoService {
    
    @Autowired
    EfectivoRepository efectivoRepository;

    public boolean add(int idUser, int idSucursal, double cantidad) {
        return efectivoRepository.add(idUser, idSucursal, cantidad);
    }

    public double totalEfectivo(int idUser) {
        return efectivoRepository.totalEfectivo(idUser);
    }
}
