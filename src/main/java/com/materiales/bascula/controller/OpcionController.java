package com.materiales.bascula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materiales.bascula.service.OptionService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/catalogos/opciones")
public class OpcionController {

    @Autowired
    OptionService optionService;

    @GetMapping("/")
    public String index(Model model) {
        //model.addAttribute("materiales", materialService.findAll());
        return "pages/opcion/index";
    }

    
    @GetMapping("/ticket_logo")
    public @ResponseBody boolean isPrintLogo() {
        return optionService.getBoolean("print_logo");
    }
    
    @PostMapping("/ticket_logo")
    public @ResponseBody boolean updatePrintLogo(boolean valor) {
        return  optionService.setBoolean("print_logo",valor);
    }

}
