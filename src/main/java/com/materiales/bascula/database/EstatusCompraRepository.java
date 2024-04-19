package com.materiales.bascula.database;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.DetalleModel;
import com.materiales.bascula.model.EstatusCompra;

import jakarta.annotation.PostConstruct;

@Repository
public class EstatusCompraRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public List<EstatusCompra> find() {
        return jdbcTemplate.query("SELECT * FROM estatusCompra", new RowMapper<EstatusCompra>() {
            public EstatusCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
                EstatusCompra estatusCompra = new EstatusCompra();
                estatusCompra.setId(rs.getInt("id_estatus"));
                estatusCompra.setNombre(rs.getString("nombre"));
                return estatusCompra;
            }
        });
    }

    public List<DetalleModel> findDetalle(int id) {
        return jdbcTemplate.query("SELECT m.code codigo, peso_bruto, peso_neto, tara, precio, total " +
                "FROM comprasDetalle cd LEFT JOIN materiales m USING (id_material) WHERE cd.id_compra= ? ",
                new RowMapper<DetalleModel>() {
                    public DetalleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        DetalleModel dm = new DetalleModel();
                        dm.setCodigo(rs.getString("codigo"));
                        dm.setTara(rs.getDouble("tara"));
                        dm.setPesoBruto(rs.getDouble("peso_bruto"));
                        dm.setPesoNeto(rs.getDouble("peso_neto"));
                        dm.setPrecio(rs.getDouble("precio"));
                        dm.setTotal(rs.getDouble("total"));
                        return dm;
                    }
                }, id);
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
