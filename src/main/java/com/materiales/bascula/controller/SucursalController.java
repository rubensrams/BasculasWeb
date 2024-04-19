package com.materiales.bascula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materiales.bascula.service.SucursalService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/catalogos/sucursal")
public class SucursalController {

    @Autowired
    SucursalService sucursalService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sucursales", sucursalService.findAll());
        return "pages/sucursal/index";
    }

    @PostMapping("/update")
    public @ResponseBody boolean update(int idSucursal, String nombre) {
        return sucursalService.update(idSucursal, nombre);
    }

    @PostMapping("/insert")
    public @ResponseBody boolean insert(String nombre) {
        return sucursalService.insert(nombre);
    }
}
