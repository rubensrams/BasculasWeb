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

import com.materiales.bascula.model.Rol;

import jakarta.annotation.PostConstruct;

@Repository
public class RolRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Rol> findAll() {

         return jdbcTemplate.query("SELECT * FROM roles",
                new RowMapper<Rol>() {
                    @Override
                    @Nullable
                     public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
                         Rol rol = new Rol();
                         rol.setId(rs.getInt("id_rol"));
                         rol.setNombre(rs.getString("nombre"));
                        return rol;
                    }
                });
    }
}
