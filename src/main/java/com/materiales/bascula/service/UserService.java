package com.materiales.bascula.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.UserReporsitory;
import com.materiales.bascula.model.OrderBy;
import com.materiales.bascula.model.User;
import com.materiales.bascula.model.UserDatatable;

@Service
public class UserService {

    @Autowired
    private UserReporsitory userReporsitory;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> find() {
        return userReporsitory.find();
    }

    public UserDatatable find(int rows, int page, int idUser, OrderBy order, int conectado) {
        
        UserDatatable userDatatable = new UserDatatable();
        userDatatable.setCount(userReporsitory.countByQuery(rows, page, idUser, order,conectado));
        userDatatable.setUsers(userReporsitory.find(rows, page, idUser, order,conectado));

        return userDatatable;
    }

    public List<User> findAll() {
        return userReporsitory.find();
    }

    public User findById(int id) {
        return userReporsitory.findById(id);
    }

    public User findByUsername(String username) {
        return userReporsitory.findByUserName(username);
    }
    
    public boolean update(User user) {
        return userReporsitory.update(user);
    }

    public boolean insert(User user) {
        return userReporsitory.insert(user);
    }

    public boolean existById(int id) {
        return userReporsitory.existById(id);
    }

    public int count() {
        return userReporsitory.count();
    }

    public boolean changePassword(String password, int id) {
        return userReporsitory.changePassword(passwordEncoder.encode(password), id);
    }

    public boolean matchPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public boolean changeAvatar(int idUser, String newName) {
       return userReporsitory.changeAvatar(idUser, newName);
    }
}
