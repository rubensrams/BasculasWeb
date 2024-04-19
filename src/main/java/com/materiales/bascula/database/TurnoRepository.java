package com.materiales.bascula.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class TurnoRepository {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public boolean existById(int idUser) {
        return jdbcTemplate.queryForObject("SELECT EXISTS (" +
                "SELECT * FROM turnos WHERE id_user = ? AND DATE(fecha) = DATE(NOW()) ORDER BY fecha DESC LIMIT 1)",
                Boolean.class, idUser);
    }

    public boolean isOpen(int idUser) {
        return jdbcTemplate.queryForObject(
                "SELECT CASE WHEN (estatus = 'abierto') THEN TRUE ELSE FALSE END FROM turnos WHERE id_user = ? AND DATE(fecha) = DATE(NOW()) ORDER BY fecha DESC LIMIT 1",
                Boolean.class, idUser);
    }

    public boolean open(int idUser) {
        return jdbcTemplate.update(
                "INSERT INTO turnos (estatus,id_user,fecha) VALUES ('abierto',?, NOW())", idUser) > 0;
    }

    public boolean close(int idUser) {
        return jdbcTemplate.update(
                "INSERT INTO turnos (estatus,id_user,fecha) VALUES ('cerrado',?, NOW())", idUser) > 0;
    }
    
    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


}
