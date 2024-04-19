package com.materiales.bascula.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.Compra;
import com.materiales.bascula.model.CompraModel;
import com.materiales.bascula.model.CompraReporte;
import com.materiales.bascula.model.CompraReporteExcel;
import com.materiales.bascula.model.Detalle;
import com.materiales.bascula.model.ExcelDetalle;
import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.ReporteCompra;

import jakarta.annotation.PostConstruct;

@Repository
public class CompraRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public class CompraRowMapper implements RowMapper<ReporteCompra> {

        @Override
        public ReporteCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteCompra r = new ReporteCompra();
            r.setIdCompra(rs.getInt("id_compra"));
            r.setFolio(rs.getInt("folio"));
            r.setRecuperador(rs.getString("recuperador"));
            r.setTotal(rs.getDouble("total"));
            r.setSucursal(rs.getString("sucursal"));
            r.setUser(rs.getString("user"));
            r.setEstatus(rs.getString("estatus"));
            r.setFecha(rs.getDate("fecha"));
            return r;
        }

    }

    public List<ReporteCompra> compras(Date fechaIni, Date fechaFin, int idUser) {
        String sql = "SELECT id_compra, folio, r.nombre recuperador, total," +
                " s.nombre sucursal, u.name user, e.nombre estatus, fecha " +
                "FROM compras c " +
                "LEFT JOIN recuperadores r USING (id_recuperador) " +
                "LEFT JOIN estatusCompra e USING (id_estatus) " +
                "LEFT JOIN users u USING (id_user) " +
                "LEFT JOIN sucursales s ON(c.id_sucursal = s.id_sucursal) " +
                "WHERE fecha BETWEEN ? AND ? AND c.id_user = ?";
        return jdbcTemplate.query(sql, new CompraRowMapper(), fechaIni, fechaFin, idUser);

    }

    public int getLastFolio(int idUser) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT MAX(folio) FROM compras WHERE id_user = ? AND DATE(fecha) = DATE(NOW());",
                    Integer.class, idUser) + 1;
        } catch (NullPointerException ex) {
            return 1; // si no hay ningun folio regresa el numero 1
        }

    }

    public int add(Compra compra) {
        return jdbcTemplate.queryForObject("CALL nueva_compra (?,?,?,?,?)", Integer.class, compra.getIdRecuperador(),
                compra.getTotal(), compra.getIdSucursal(), compra.getIdUsuario(), compra.getIdFlete());
    }

    public void detalle(int idCompra, List<Detalle> detalle) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO comprasDetalle (id_compra,id_material,peso_bruto,peso_neto, tara, precio, total) VALUES (?,?,?,?,?,?,?) ",
                detalle, 100, (PreparedStatement ps, Detalle d) -> {
                    ps.setInt(1, idCompra);
                    ps.setInt(2, d.getIdProducto());
                    ps.setDouble(3, d.getPesoBruto());
                    ps.setDouble(4, d.getPesoNeto());
                    ps.setDouble(5, d.getTara());
                    ps.setDouble(6, d.getPrecioNegociado());
                    ps.setDouble(7, d.getTotal());
                });
    }

    public double comprasDelDia(int idUser) {
        Double d = jdbcTemplate.queryForObject("CALL ventas_del_dia(?)", Double.class, idUser);
        return d == null ? 0 : d;
    }

    public List<CompraReporte> comprasTable(Date fechaIni, Date fechaFin, int idSucursal, String searchUser, int rows,
            int page, OrderBy order) {
        return jdbcTemplate.query("CALL ventas (?, ?, ?, ?, ?, ?, ?)", new RowMapper<CompraReporte>() {
            @Override
            @Nullable
            public CompraReporte mapRow(ResultSet rs, int rowNum) throws SQLException {
                CompraReporte cdt = new CompraReporte();
                cdt.setIdCompra(rs.getInt(("id_compra")));
                cdt.setFolio(rs.getInt("folio"));
                cdt.setFecha(rs.getDate("fecha"));
                cdt.setTotal(rs.getDouble("total"));
                cdt.setUsuario(rs.getString("usuario"));
                cdt.setRecuperador(rs.getString("recuperador"));
                cdt.setFlete(rs.getString("flete"));
                cdt.setStatus(rs.getString("estatus"));
                cdt.setSucursal(rs.getString("sucursal"));
                cdt.setMaterial(rs.getString("codigo"));
                cdt.setPesoNeto(rs.getDouble("peso_neto"));
                cdt.setPrecio(rs.getDouble("precio"));
                return cdt;
            }
        }, fechaIni, fechaFin, idSucursal, searchUser, rows * page, rows, order.getOrder());
    }

    public List<CompraReporteExcel> findReporte(int sucursal, Date fechaInicio, Date fechaFin, String nombre) {
        return jdbcTemplate.query("CALL reporte_compras(?,?,?,?)", new RowMapper<CompraReporteExcel>() {
            public CompraReporteExcel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                CompraReporteExcel c = new CompraReporteExcel();
                c.setFecha(rs.getString("fecha"));
                c.setRecuperador(rs.getString("recuperador"));
                c.setSucursal(rs.getString("sucursal"));
                c.setUsuario(rs.getString("usuario"));
                c.setEstatus(rs.getString("estatus"));
                c.setPesoNeto(rs.getString("peso_neto"));
                c.setPrecio(rs.getString("precio"));
                c.setTotal(rs.getString("total"));
                c.setFolio(rs.getString("folio"));
                c.setFlete(rs.getString("flete"));
                c.setMaterial(rs.getString("material"));
                return c;
            }
        }, sucursal, fechaInicio, fechaFin, nombre);
    }

    public int countByQuery(Date fechaIni, Date fechaFin, int idSucursal, String searchUser) {
        return jdbcTemplate.queryForObject("CALL count_ventas(?, ?, ?, ?)", Integer.class, fechaIni, fechaFin,
                idSucursal, searchUser);
    }

    public boolean cancel(int idCompra) {
        return jdbcTemplate.update("UPDATE compras SET id_estatus = 2 WHERE id_compra = ?", idCompra) > 0;
    }

    public boolean existById(int id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM compras WHERE id_compra = ?)", Boolean.class,
                id);
    }

    public CompraModel find(int id) {
        return jdbcTemplate.queryForObject("CALL compra_simple(?)", new RowMapper<CompraModel>() {

            public CompraModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                CompraModel compra = new CompraModel();
                compra.setIdCompra(rs.getInt("id_compra"));
                compra.setFolio(rs.getInt("folio"));
                compra.setTotal(rs.getDouble("total"));
                compra.setFecha(rs.getDate("fecha"));
                compra.setIdEstatus(rs.getInt("id_estatus"));
                compra.setUsuario(rs.getString("usuario"));
                compra.setSucursal(rs.getString("sucursal"));
                compra.setRecuperador(rs.getString("recuperador"));
                try {
                    compra.setFlete(rs.getString("flete"));
                } catch (Exception ex) {
                    
                }
                return compra;
            }
        }, id);
    }

    public boolean updateStatusCompra(int idCompra, int estatus) {
        return jdbcTemplate.update("UPDATE compras SET id_estatus = ? WHERE id_compra = ? ", estatus, idCompra) > 0;
    }

    public List<ExcelDetalle> simpleCompra(int id) {
        return jdbcTemplate.query("CALL excel_detalle(?)", new RowMapper<ExcelDetalle>() {

            public ExcelDetalle mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                ExcelDetalle e = new ExcelDetalle();
                e.setRecuperador(rs.getString("recuperador"));
                e.setFolio(rs.getInt("folio"));
                e.setUsuario(rs.getString("usuario"));
                e.setSucursal(rs.getString("sucursal"));
                e.setTotal(rs.getDouble("total"));
                e.setFecha(rs.getString("fecha"));
                e.setCode(rs.getString("code"));
                e.setPesoBruto(rs.getDouble("peso_bruto"));
                e.setPesoNeto(rs.getDouble("peso_neto"));
                e.setTara(rs.getDouble("tara"));
                e.setPrecio(rs.getDouble("precio"));
                e.setSubtotal(rs.getDouble("subtotal"));
                try{
                    e.setFlete(rs.getString("flete"));
                }catch(Exception ex){}
                return e;
            }

        }, id);
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
