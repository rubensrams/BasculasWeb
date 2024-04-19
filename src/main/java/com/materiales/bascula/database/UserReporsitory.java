package com.materiales.bascula.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.User;

import jakarta.annotation.PostConstruct;

@Repository
public class UserReporsitory {

    @Autowired
    @NonNull
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(UserReporsitory.class);

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id_user"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setRfc(rs.getString("rfc"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setCity(rs.getString("city"));
            user.setState(rs.getString("state"));
            user.setIdSucursal(rs.getInt("id_sucursal"));
            user.setSucursal(rs.getString("sucursal"));
            user.setRol(rs.getString("rol"));
            user.setAvatar(rs.getString("avatar"));
            user.setColor(rs.getString("color"));
            return user;
        }
    }

    class UserShortRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id_user"));
            user.setUsername(rs.getString("username"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("lastname"));
            user.setSucursal(rs.getString("sucursal"));
            user.setRol(rs.getString("rol"));
            user.setColor(rs.getString("color"));
            user.setActive(rs.getString("activo"));
            return user;
        }
    }

    /***
     * Method for search a user in login, only enabled users as returns.
     * 
     * @param userName
     * @return User
     */
    public User findByUserName(String userName) {
        try {
            //solo los de centro de acopio tienen acceso a la aplicacion local AND id_rol = 4
            return jdbcTemplate.queryForObject(
                    "SELECT u.*,s.nombre sucursal, r.nombre_corto rol FROM users u LEFT JOIN sucursales s USING (id_sucursal) LEFT JOIN roles r USING (id_rol) WHERE username = ? AND enabled IS TRUE",
                    new UserRowMapper(), userName);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public User findById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT u.*,s.nombre sucursal, r.nombre rol FROM users u LEFT JOIN sucursales s USING (id_sucursal) LEFT JOIN roles r USING (id_rol) WHERE id_user = ?",
                    new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<User> find() {
        return jdbcTemplate.query(
                "SELECT id_user,username,enabled,name,lastname,s.nombre sucursal,r.nombre rol,color, 0 activo FROM users u LEFT JOIN sucursales s USING (id_sucursal)  LEFT JOIN roles r USING (id_rol)",
                new UserShortRowMapper());
    }

    public List<User> find(int rows, int page, int idUser, OrderBy order, int conectado) {
        return jdbcTemplate.query(
                "SELECT id_user,username,enabled,name,lastname,s.nombre sucursal,r.nombre rol,color," +
                        " (SELECT estatus FROM turnos WHERE id_turno = (" +
                        "SELECT id_turno FROM turnos WHERE id_user = u.id_user  AND DATE(fecha) = DATE(NOW()) ORDER BY fecha DESC LIMIT 1"
                        +
                        ")) activo" +
                        " FROM users u LEFT JOIN sucursales s USING (id_sucursal) LEFT JOIN roles r USING (id_rol)" +
                        ( idUser == 0 ? "" :" WHERE id_user = " + idUser + " ")
                        + (conectado == -1 ? "" : "HAVING activo IS " + (conectado == 1 ? "NOT NULL" : "NULL")) + " "
                        + order.getOrder()
                        + " LIMIT ?, ?",
                new UserShortRowMapper(), rows * page, rows);
    }

    public Integer countByQuery(int rows, int page, int idUser, OrderBy order, int conectado) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*)" +
                        " FROM users u LEFT JOIN sucursales s USING (id_sucursal) LEFT JOIN roles r USING (id_rol)" +
                        ( idUser == 0 ? "" :" WHERE id_user = " + idUser + " "),
                Integer.class);
    }

    public boolean existById(int id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM users WHERE id_user = ?)", Boolean.class, id);
    }

    public boolean update(User user) {
        try {
            String query = "UPDATE users set username = ?, enabled = ?, name = ?, lastname = ?,rfc = ?, email = ?, address = ?, city = ?, state= ?, id_sucursal= ?, id_rol= ? WHERE id_user = ?";
            return jdbcTemplate.update(query, user.getUsername(), user.isEnabled(), user.getName(), user.getLastName(),
                    user.getRfc(), user.getEmail(), user.getAddress(), user.getCity(), user.getState(),
                    user.getSucursal(),
                    user.getRol(), user.getId()) > 0;
        } catch (DuplicateKeyException ex) {
            logger.warn("duplicate username for: " + user.getUsername());
            return false;
        }
    }

    public boolean insert(User user) {
        try {
            String query = "INSERT INTO users (username, enabled, name, lastname, rfc, email, state, id_sucursal, id_rol, color,avatar) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,'');";
            return jdbcTemplate.update(query, user.getUsername(), user.isEnabled(), user.getName(), user.getLastName(),
                    user.getRfc(), user.getEmail(), user.getState(),
                    user.getSucursal(),
                    user.getRol(), user.getColor()) > 0;
        } catch (DuplicateKeyException ex) {
            logger.warn("duplicate username for: " + user.getUsername());
            return false;
        }
    }

    public Integer count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }

    public boolean changePassword(String password, int id) {
        return jdbcTemplate.update("UPDATE users set password = ? WHERE id_user = ?", password, id) > 0;
    }

    public boolean changeAvatar(int idUser, String newName) {
        return jdbcTemplate.update("UPDATE users set Avatar = ? WHERE id_user= ?", newName, idUser) > 0;
    }

    @PostConstruct
    private void load() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}