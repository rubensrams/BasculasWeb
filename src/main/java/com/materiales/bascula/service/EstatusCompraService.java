package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.EstatusCompraRepository;
import com.materiales.bascula.model.DetalleModel;
import com.materiales.bascula.model.EstatusCompra;

@Service
public class EstatusCompraService {

    @Autowired
    EstatusCompraRepository estatusCompraRepository;

    public List<EstatusCompra> find() {
        return estatusCompraRepository.find();
    }

    public List<DetalleModel> findDetalle(int id) {
        return estatusCompraRepository.findDetalle(id);
    }

}
