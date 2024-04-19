package com.materiales.bascula.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materiales.bascula.model.CompraDatatable;
import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.service.CompraService;
import com.materiales.bascula.service.EstatusCompraService;
import com.materiales.bascula.service.SucursalService;
import com.materiales.bascula.utils.UserExcelGeneratorCompras;
import com.materiales.bascula.utils.UserExcelGeneratorComprasSimple;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;

@Controller
@RequestMapping("/catalogos/compra")
public class CompraController {

    @Autowired
    CompraService compraService;

    @Autowired
    SucursalService sucursalService;

    @Autowired
    EstatusCompraService estatusCompraService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sucursales", sucursalService.findAll());
        return "pages/compra/index";
    }

    @PostMapping("/get")
    public @ResponseBody CompraDatatable user(@Max(value = 100) int rows, int page, OrderBy order,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaIni,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaFin,
            int idSucursal, String searchUser) {
        return compraService.comprasTable(fechaIni, fechaFin, idSucursal, rows, page, order, searchUser);
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable @Digits(integer = 6, fraction = 0) String id, Model model) {
        if (!compraService.existById(Integer.parseInt(id))) {
            return "redirect:/catalogos/compra/";
        }
        model.addAttribute("titulo", "Mostrar");
        model.addAttribute("estatusCompra", estatusCompraService.find());
        model.addAttribute("compra", compraService.find(Integer.parseInt(id)));
        model.addAttribute("detalles", estatusCompraService.findDetalle(Integer.parseInt(id)));
        return "pages/compra/update";
    }

    @PostMapping("/update")
    public @ResponseBody boolean updateStatusCompra(int idCompra, int estatus) {
        return compraService.updateStatusCompra(idCompra, estatus);
    }

    @GetMapping("/single/export/{id}")
    public void exportIntoExcelFile(HttpServletResponse response,@PathVariable int id) throws IOException {
        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=Compras_Detalle_"
                + new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(new Date())
                + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);
        UserExcelGeneratorComprasSimple generator = new UserExcelGeneratorComprasSimple(compraService.compra(id));
        generator.generateExcelFile(response);
    }

    @GetMapping("/export")
    public void exportIntoExcelFile(HttpServletResponse response, String nombre,
            int sucursal, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) throws IOException {
        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=Compras_"
                + new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(new Date())
                + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);
        UserExcelGeneratorCompras generator = new UserExcelGeneratorCompras(compraService.findReporte(sucursal, fechaInicio, fechaFin, nombre));
        generator.generateExcelFile(response);
    }
}
