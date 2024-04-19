package com.materiales.bascula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materiales.bascula.database.OptionRepository;

@Service
public class OptionService {

    @Autowired
    OptionRepository optionRepository;

    public boolean getBoolean(String option) {
        return optionRepository.getBoolean(option);
    }

    public boolean setBoolean(String option, boolean valor) {
        return optionRepository.setBoolean(option, valor);
    }

}
