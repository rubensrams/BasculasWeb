package com.materiales.bascula.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class CierreRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public String save(int idUser, double entrada, double salida) {
        return jdbcTemplate.queryForObject("CALL save_cierre(?,?,?)", String.class, idUser, entrada, salida);
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
