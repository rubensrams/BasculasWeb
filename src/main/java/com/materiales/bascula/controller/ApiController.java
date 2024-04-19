package com.materiales.bascula.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.materiales.bascula.model.Compra;
import com.materiales.bascula.model.CompraReporteExcel;
import com.materiales.bascula.model.Detalle;
import com.materiales.bascula.model.Material;
import com.materiales.bascula.model.Recuperador;
import com.materiales.bascula.model.ReporteCompra;
import com.materiales.bascula.model.User;
import com.materiales.bascula.service.CierreService;
import com.materiales.bascula.service.CompraService;
import com.materiales.bascula.service.EfectivoService;
import com.materiales.bascula.service.MaterialService;
import com.materiales.bascula.service.OptionService;
import com.materiales.bascula.service.RecuperadorService;
import com.materiales.bascula.service.TurnoService;
import com.materiales.bascula.service.UserService;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    @Value("${basculas.key}")
    private String apiKey;

    @Autowired
    UserService userService;

    @Autowired
    MaterialService materialService;

    @Autowired
    TurnoService turnoService;

    @Autowired
    EfectivoService efectivoService;

    @Autowired
    RecuperadorService recuperadorService;

    @Autowired
    CompraService compraService;

    @Autowired
    CierreService cierreService;

    @Autowired
    OptionService optionService;

    private void isValidConnection(String key) {
        if (!apiKey.equals(key)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/user/get/")
    public User user(@NotEmpty(message = "necesario") String username,
            @NotEmpty(message = "necesario") String password, @RequestHeader("key") String key) {
        isValidConnection(key);
        User user = userService.findByUsername(username);
        if (user != null && userService.matchPassword(password, user.getPassword())) {
            user.setPassword("");
            return user;
        }
        return new User();
    }

    @PostMapping("/materials/get/")
    public List<Material> materials(@RequestHeader("key") String key) {
        isValidConnection(key);
        return materialService.findAll();
    }

    @PostMapping("/details/get/")
    public List<Detalle> detailsMaterials(@RequestHeader("key") String key, String idCompra) {
        isValidConnection(key);
        return materialService.find(idCompra);
    }

    @PostMapping("/turno/get/")
    public Map<String, Boolean> turno(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("openTurn", turnoService.isOpen(idUser));
    }

    @PostMapping("/turno/open/")
    public boolean abrirTurno(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return turnoService.add(idUser);
    }

    @PostMapping("/turno/close/")
    public boolean cerrarTurno(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return turnoService.close(idUser);
    }

    @PostMapping("/efectivo/add/")
    public boolean cerrarTurno(int idUser, int idSucursal, double cantidad, @RequestHeader("key") String key) {
        isValidConnection(key);
        return efectivoService.add(idUser, idSucursal, cantidad);
    }

    @PostMapping("/efectivo/total/")
    public Map<String, Double> totalEfectivo(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("efectivo", efectivoService.totalEfectivo(idUser));
    }

    @PostMapping("/compra/add/")
    public boolean compra(@RequestBody Compra compra, @RequestHeader("key") String key) {
        isValidConnection(key);
        try {
            compraService.add(compra);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @PostMapping("/recuperador/get/")
    public List<Recuperador> recuperador(@Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String search,
            @RequestHeader("key") String key) {
        isValidConnection(key);
        return recuperadorService.findbyName(search);
    }

    @PostMapping("/recuperador/add/")
    public Map<String, Integer> guardarRecuperador(
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String nombre,
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String apPaterno,
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String apMaterno,
            String correo,
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String telefono,
            @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("id", recuperadorService.save(nombre, apPaterno, apMaterno, correo, telefono));
    }

    @PostMapping("/recuperador/search/")
    public List<Recuperador> recuperadorFind(
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String nombre,
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String apPaterno,
            @Pattern(message = "Alfanumerico", regexp = "^[A-Za-z0-9]*$") String apMaterno,
            String idRecuperador, @RequestHeader("key") String key) {
        isValidConnection(key);
        return recuperadorService.findbyNameAndId(idRecuperador, nombre, apPaterno, apMaterno);
    }

    @PostMapping("/compras/get/")
    public List<ReporteCompra> compras(int idUser, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaIni,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaFin, @RequestHeader("key") String key) {
        isValidConnection(key);
        return compraService.compras(fechaIni, fechaFin, idUser);
    }

    @PostMapping("/compras/folio/")
    public Map<String, Integer> compras(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("folio", compraService.getLastFolio(idUser));
    }

    @PostMapping("/compras/cierre/")
    public Map<String, Double> cierre(int idUser, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("total", compraService.comprasDelDia(idUser));
    }

    @PostMapping("/compras/cancel/")
    public Map<String, Boolean> cancelCompra(int idCompra, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("response", compraService.cancel(idCompra));
    }

    @PostMapping("/cierre/save/")
    public Map<String, String> cierre(int idUser, double entrada, double salida, @RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("response", cierreService.save(idUser, entrada, salida));
    }

    @PostMapping("/options/impresion/")
    public Map<String, Boolean> ticket(@RequestHeader("key") String key) {
        isValidConnection(key);
        return Collections.singletonMap("printLogo", optionService.getBoolean("print_logo"));
    }

    @PostMapping("/report/compra/")
    public List<CompraReporteExcel> reporteCompra(int sucursal,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinal, String nombre,
            @RequestHeader("key") String key) {
        isValidConnection(key);
        System.out.println(fechaInicio);
        System.out.println(fechaFinal);
        return compraService.findReporte(sucursal, fechaInicio, fechaFinal, nombre);
    }

}
