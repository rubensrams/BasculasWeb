package com.materiales.bascula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materiales.bascula.service.MaterialService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/catalogos/material")
public class MaterialController {

    @Autowired
    MaterialService materialService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("materiales", materialService.findAll());
        return "pages/material/index";
    }
    @PostMapping("/update")
    public @ResponseBody boolean update(int material, double precio) {
        return materialService.update(material, precio);
    }
    
    @PostMapping("/insert")
    public @ResponseBody boolean insert(String nombre,double precio) {
        return materialService.insert(nombre, precio);
    }

}
