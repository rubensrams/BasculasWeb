package com.materiales.bascula.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.Detalle;
import com.materiales.bascula.model.Material;

import jakarta.annotation.PostConstruct;

@Repository
public class MaterialRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    class MaterialRowMapper implements RowMapper<Material> {
        @Override
        public Material mapRow(ResultSet rs, int rowNum) throws SQLException {
            Material material = new Material();
            material.setIdMaterial(rs.getInt("id_material"));
            material.setCode(rs.getString("code"));
            material.setDescription(rs.getString("description"));
            material.setPrice(rs.getDouble("price"));
            return material;
        }
    }

    public List<Material> findAll() {
        return jdbcTemplate.query("SELECT * FROM materiales", new MaterialRowMapper());
    }

    public List<Detalle> find(String idCompra) {
        return jdbcTemplate.query("SELECT code material,peso_bruto,peso_neto,tara,precio,total " +
                " FROM comprasDetalle cd LEFT JOIN materiales m using (id_material) WHERE id_compra = ?",
                new RowMapper<Detalle>() {
                    public Detalle mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Detalle d = new Detalle();
                        d.setNombreProducto(rs.getString("material"));
                        d.setPesoBruto(rs.getDouble("peso_bruto"));
                        d.setPesoNeto(rs.getDouble("peso_neto"));
                        d.setTara(rs.getDouble("tara"));
                        d.setPrecioNegociado(rs.getDouble("precio"));
                        d.setTotal(rs.getDouble("total"));
                        return d;
                    }
                }, idCompra);
    }

    public boolean save(int idMaterial, double precio) {
        return jdbcTemplate.update("UPDATE materiales SET price = ? WHERE id_material = ?", precio, idMaterial) > 0;
    }

    public boolean save(String nombre, double precio) {
        return jdbcTemplate.update("INSERT INTO materiales (code,price) VALUES (UPPER(?),?)",nombre, precio) > 0;
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
