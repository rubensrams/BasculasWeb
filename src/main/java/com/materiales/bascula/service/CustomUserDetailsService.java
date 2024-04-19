package com.materiales.bascula.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.UserReporsitory;
import com.materiales.bascula.model.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserReporsitory userReporsitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.materiales.bascula.model.User user = userReporsitory.findByUserName(username);
        if (user != null) {
            if ("COORDINADOR".equals(user.getRol()) || "AUXILIAR".equals(user.getRol())) {
                throw new UsernameNotFoundException("no tiene permisos para entrar");
            }
            CustomUserDetails customUser = new CustomUserDetails(user.getUsername(), user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRol())));
            customUser.setFullName(user.getName());
            customUser.setInitials((user.getName().substring(0, 1) + user.getLastName().substring(0, 1)).toUpperCase());
            customUser.setColor(user.getColor());
            return customUser;
        } else {
            throw new UsernameNotFoundException("usuario y/o password incorrectos");
        }
    }

}