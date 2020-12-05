package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.repository.ProjektRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjektServiceImpl implements ProjektService {
    private ProjektRepository projektRepository;

    @Autowired
    public ProjektServiceImpl(ProjektRepository projektRepository) {
        this.projektRepository = projektRepository;
    }

    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }

    public List<Projekt> getProjekty()
    {
        return projektRepository.findAll();
    }


}
