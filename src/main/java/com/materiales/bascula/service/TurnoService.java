package com.materiales.bascula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.TurnoRepository;

@Service
public class TurnoService {
    
    @Autowired
    TurnoRepository turnoRepository;

    public boolean isOpen(int idUser) {
        if (turnoRepository.existById(idUser)) {
            return turnoRepository.isOpen(idUser);
        }
        else {
            return false;   
        }
    }

    public boolean add(int idUser) {
        return turnoRepository.open(idUser);
    }

    public boolean close(int idUser) {
        return turnoRepository.close(idUser);
    }  

}
