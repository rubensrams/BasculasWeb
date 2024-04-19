package com.materiales.bascula.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.Recuperador;
import com.materiales.bascula.model.RecuperadorDatatable;
import com.materiales.bascula.service.RecuperadorService;
import com.materiales.bascula.utils.UserExcelGeneratorSocio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;

@Controller
@RequestMapping("/catalogos/socio")
public class SocioController {

    @Autowired
    RecuperadorService recuperadorService;
    Logger logger = LoggerFactory.getLogger(SocioController.class);

    @GetMapping("/")
    public String index() {
        return "pages/socio/index";
    }

    @PostMapping("/get")
    public @ResponseBody RecuperadorDatatable user(@Max(value = 100) int rows, int page, OrderBy order,
            @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Non Alphanumeric") String search) {
        return recuperadorService.find(rows, page, search, order);
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("titulo", "Agregar");
        model.addAttribute("recuperador", new Recuperador());
        return "pages/socio/update";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable @Digits(integer = 6, fraction = 0) String id, Model model) {
        if (!recuperadorService.existById(Integer.parseInt(id))) {
            return "redirect:/catalogos/socio/";
        }
        model.addAttribute("titulo", "Actualizar");
        model.addAttribute("recuperador", recuperadorService.find(Integer.parseInt(id)));
        return "pages/socio/update";
    }

    @PostMapping("/update")
    public @ResponseBody boolean addOrUpdate(@Valid Recuperador recuperador) {
        logger.info(recuperador.toString());
        if (recuperador.getIdRecuperador() == 0) { // new user
            return recuperadorService.insert(recuperador);
        } else {
            return recuperadorService.update(recuperador);
        }
    }

    @GetMapping("/export")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=Socios_"
                + new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(new Date())
                + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);
        UserExcelGeneratorSocio generator = new UserExcelGeneratorSocio(recuperadorService.findAll());
        generator.generateExcelFile(response);
    }

}
