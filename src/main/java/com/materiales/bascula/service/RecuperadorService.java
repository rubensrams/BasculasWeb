package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.RecuperadorRepository;
import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.Recuperador;
import com.materiales.bascula.model.RecuperadorDatatable;

import jakarta.validation.Valid;

@Service
public class RecuperadorService {

    @Autowired
    RecuperadorRepository recuperadorRepository;

    public List<Recuperador> findbyName(String search) {
        return recuperadorRepository.findbyName(search);
    }

    public List<Recuperador> findbyNameAndId(String idRecuperador, String nombre, String apPaterno, String apMaterno) {
        return recuperadorRepository.findbyNameAndId(idRecuperador, nombre, apPaterno, apMaterno);
    }

    public int save(String nombre, String apPaterno, String apMaterno, String email, String telefono) {
        return recuperadorRepository.save(nombre, apPaterno, apMaterno, email, telefono);
    }

    public RecuperadorDatatable find(int rows, int page, String search, OrderBy order) {
        RecuperadorDatatable recuperadorDatatable = new RecuperadorDatatable();
        recuperadorDatatable.setRecuperadores(recuperadorRepository.find(rows, page, search, order));
        recuperadorDatatable.setCount(recuperadorRepository.countByQuery(search));
        return recuperadorDatatable;
    }

    public List<Recuperador> findAll() {
        return recuperadorRepository.findAll();
    }

    public boolean existById(int idRecuperador) {
        return recuperadorRepository.existById(idRecuperador);
    }

    public Recuperador find(int idRecuperador) {
        return recuperadorRepository.find(idRecuperador);
    }

    public boolean insert(@Valid Recuperador recuperador) {
        return recuperadorRepository.insert(recuperador);
    }

    public boolean update(@Valid Recuperador recuperador) {
        return recuperadorRepository.update(recuperador);
    }

}
