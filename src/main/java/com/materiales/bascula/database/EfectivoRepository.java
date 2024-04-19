package com.materiales.bascula.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class EfectivoRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public boolean add(int idUser, int idSucursal, double cantidad) {
        return jdbcTemplate.update(
                "INSERT INTO efectivo (id_user, id_sucursal, cantidad, fecha) VALUES (?, ?, ?,NOW())",
                idUser, idSucursal, cantidad) > 0;
    }

    public double totalEfectivo(int idUser) {
        Double d = jdbcTemplate.queryForObject("CALL total_efectivo(?)", Double.class, idUser);
        return d == null ? 0 : d;
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
