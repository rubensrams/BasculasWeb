package com.materiales.bascula.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.Recuperador;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Repository
public class RecuperadorRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public class RecuperadorRowMapper implements RowMapper<Recuperador> {

        @Override
        public Recuperador mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Recuperador recuperador = new Recuperador();
            recuperador.setNombre(rs.getString("nombre"));
            recuperador.setApPaterno(rs.getString("apellidoPaterno"));
            recuperador.setApMaterno(rs.getString("apellidoMaterno"));
            recuperador.setIdRecuperador(rs.getInt("id_recuperador"));
            recuperador.setEmail(rs.getString("email"));
            recuperador.setTelefono(rs.getString("telefono"));
            return recuperador;
        }

    }

    public Recuperador find(int idRecuperador) {
        return jdbcTemplate.queryForObject("SELECT * FROM recuperadores WHERE id_recuperador = ?",
                new RecuperadorRowMapper(), idRecuperador);
    }

    public List<Recuperador> findbyName(String search) {
        return jdbcTemplate.query("SELECT * FROM recuperadores WHERE nombre like '%" + search + "%'",
                new RecuperadorRowMapper());
    }

    public List<Recuperador> findbyNameAndId(String idRecuperador, String nombre, String apPaterno, String apMaterno) {
        return jdbcTemplate.query("CALL find_recuperador (?,?,?,?)", new RecuperadorRowMapper(), nombre, apPaterno,
                apMaterno, idRecuperador);
    }

    public int save(String nombre, String apPaterno, String apMaterno, String email, String telefono) {
        return jdbcTemplate.queryForObject("CALL save_recuperador (?,?,?,?,?)", Integer.class, nombre, apPaterno,
                apMaterno, email, telefono);
    }

    public List<Recuperador> find(int rows, int page, String search, OrderBy order) {
        return jdbcTemplate.query(
                "SELECT id_recuperador,UPPER(nombre) nombre,UPPER(apellidoPaterno) apellidoPaterno,UPPER(apellidoMaterno) apellidoMaterno,"
                        +
                        "IFNULL(direccion,'') direccion, IFNULL(email,'') email, IFNULL(telefono,'') telefono" +
                        " FROM recuperadores WHERE CONCAT(nombre,' ',apellidoPaterno, ' ', apellidoMaterno)" +
                        " LIKE '%" + search + "%' " + order.getOrder() + " LIMIT ? , ? ",
                new RecuperadorRowMapper(), rows * page, rows);
    }

    public Integer countByQuery(String search) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM recuperadores" +
                        " WHERE CONCAT(nombre,' ',apellidoPaterno,' ', apellidoMaterno) LIKE '%" + search + "%' ",
                Integer.class);
    }

    public boolean existById(int idRecuperador) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM recuperadores WHERE id_recuperador = ?)",
                Boolean.class, idRecuperador);
    }

    public boolean insert(@Valid Recuperador recuperador) {
        String sql = "INSERT INTO recuperadores (nombre,apellidoPaterno,apellidoMaterno,email,telefono) VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql, recuperador.getNombre(), recuperador.getApPaterno(),
                recuperador.getApMaterno(), recuperador.getEmail(),
                recuperador.getTelefono()) > 0;
    }

    public boolean update(@Valid Recuperador recuperador) {
        String query = "UPDATE recuperadores SET nombre = ?, apellidoPaterno = ?," +
                "apellidoMaterno = ?, email = ?, telefono = ? WHERE id_recuperador = ?";
        return jdbcTemplate.update(query, recuperador.getNombre(), recuperador.getApPaterno(),
                recuperador.getApMaterno(), recuperador.getEmail(),
                recuperador.getTelefono(), recuperador.getIdRecuperador()) > 0;
    }

    public List<Recuperador> findAll() {
        return jdbcTemplate.query("SELECT * FROM recuperadores", new RecuperadorRowMapper());
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

}
