package com.materiales.bascula.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.User;
import com.materiales.bascula.model.UserDatatable;
import com.materiales.bascula.service.RolService;
import com.materiales.bascula.service.SucursalService;
import com.materiales.bascula.service.UserService;
import com.materiales.bascula.utils.UserExcelGenerator;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

@Controller
@Validated
@RequestMapping("/catalogos/usuario")
public class UserController {

    @Value("${basculas.avatarDirectory}")
    private String directory;
    @Autowired
    UserService userService;
    @Autowired
    RolService rolService;
    @Autowired
    SucursalService sucursalService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("usuarios", userService.find());
        return "pages/usuario/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("titulo", "Agregar");
        model.addAttribute("user", new User());
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("sucursales", sucursalService.findAll());
        return "pages/usuario/update";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable @Digits(integer = 6, fraction = 0) String id, Model model) {
        if (!userService.existById(Integer.parseInt(id))) {
            return "redirect:/catalogos/usuario/";
        }
        model.addAttribute("titulo", "Actualizar");
        model.addAttribute("user", userService.findById(Integer.parseInt(id)));
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("sucursales", sucursalService.findAll());
        return "pages/usuario/update";
    }

    @PostMapping("/update")
    public @ResponseBody boolean addUser(@Valid User user) {
        logger.debug(user.toString());
        if (user.getId() == 0) { // new user
            String colors[] = { "red", "blue", "green", "pink", "purple", "orange" };
            user.setColor("avatar-" + colors[new Random().nextInt(6)]);
            return userService.insert(user);
        } else {
            return userService.update(user);
        }
    }

    @PostMapping("/password")
    public @ResponseBody boolean password(@Size(min = 6) String password, int id) {
        return userService.changePassword(password, id);
    }

    @PostMapping("/get")
    public @ResponseBody UserDatatable user(@Max(value = 100) int rows, int page, OrderBy order,
            int idUser, int conectado) {
        return userService.find(rows, page, idUser, order,conectado);
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, MultipartFile image, int idUser) throws IOException {
        String newName = RandomStringUtils.random(16, true, true);
        Files.write(Paths.get(directory,newName), image.getBytes());
        userService.changeAvatar(idUser, "https://basculaspetstar.com/avatar/" + newName);
        return "redirect:/catalogos/usuario/update/" + idUser;
    }

    @GetMapping("/export")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=Usuarios_"
                + new SimpleDateFormat("yyyy-MM-dd_HH_mm").format(new Date())
                + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);
        UserExcelGenerator generator = new UserExcelGenerator(userService.find());
        generator.generateExcelFile(response);
    }

}
