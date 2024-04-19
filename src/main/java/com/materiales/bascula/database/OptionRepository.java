package com.materiales.bascula.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class OptionRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    @NonNull
    private DataSource dataSource;

    public boolean getBoolean(String option) {
        return jdbcTemplate.queryForObject("SELECT option_value from options WHERE option_name = ?",
                Boolean.class, option);
    }

    public boolean setBoolean(String option, boolean valor) {
        return jdbcTemplate.update("UPDATE options SET option_value = ? WHERE option_name = ?",
                String.valueOf(valor), option) > 0;
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
