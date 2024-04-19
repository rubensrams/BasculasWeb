package com.materiales.bascula.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.materiales.bascula.database.CompraRepository;
import com.materiales.bascula.model.Compra;
import com.materiales.bascula.model.CompraDatatable;
import com.materiales.bascula.model.CompraModel;
import com.materiales.bascula.model.CompraReporteExcel;
import com.materiales.bascula.model.ExcelDetalle;
import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.ReporteCompra;

@Service
public class CompraService {

    @Autowired
    CompraRepository compraRepository;

    public List<ReporteCompra> compras(Date fechaIni, Date fechaFin, int idUser) {
        return compraRepository.compras(fechaIni, fechaFin, idUser);
    }

    public List<ExcelDetalle> compra(int id) {
        return compraRepository.simpleCompra(id);
    }

    public int getLastFolio(int idUser) {
        return compraRepository.getLastFolio(idUser);
    }

    @Transactional
    public void add(Compra compra) {
        int idCompra = compraRepository.add(compra);
        compraRepository.detalle(idCompra, compra.getDetalle());
    }

    public double comprasDelDia(int idUser) {
        return compraRepository.comprasDelDia(idUser);
    }

    public boolean cancel(int idCompra) {
        return compraRepository.cancel(idCompra);
    }

    public boolean existById(int id) {
        return compraRepository.existById(id);
    }

    public CompraModel find(int id) {
        return compraRepository.find(id);
    }

    public boolean updateStatusCompra(int idCompra, int estatus) {
        return compraRepository.updateStatusCompra(idCompra,estatus);
    }

    public CompraDatatable comprasTable(Date fechaIni, Date fechaFin, int idSucursal, int rows, int page,
            OrderBy order, String searchUser) {
        CompraDatatable compraDatatable = new CompraDatatable();
        compraDatatable.setCompras(
                compraRepository.comprasTable(fechaIni, fechaFin, idSucursal, searchUser, rows, page, order));
        compraDatatable.setCount(compraRepository.countByQuery(fechaIni, fechaFin, idSucursal, searchUser));
        return compraDatatable;
    }
    
    public List<CompraReporteExcel> findReporte(int sucursal, Date fechaInicio, Date fechaFin, String nombre) {
        return compraRepository.findReporte(sucursal, fechaInicio, fechaFin, nombre);
    }

}
