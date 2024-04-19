package com.materiales.bascula.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.Sucursal;

import jakarta.annotation.PostConstruct;

@Repository
public class SucursalRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public List<Sucursal> findAll() {
        return jdbcTemplate.query("SELECT * FROM sucursales", new RowMapper<Sucursal>() {
            @Override
            @Nullable
            public Sucursal mapRow(ResultSet rs, int rowNum) throws SQLException {
                Sucursal sucursal = new Sucursal();
                sucursal.setId(rs.getInt("id_sucursal"));
                sucursal.setNombre(rs.getString("nombre"));
                return sucursal;
            }

        });
    }

    public boolean update(int idSucursal, String nombre) {
        return jdbcTemplate.update("UPDATE sucursales SET nombre = ? WHERE id_sucursal = ?", nombre, idSucursal) > 0;
    }

    public boolean insert(String nombre) {
        return jdbcTemplate.update("INSERT INTO sucursales (nombre) VALUES (?)", nombre) > 0;
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
