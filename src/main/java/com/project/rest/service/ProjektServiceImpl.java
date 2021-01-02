package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.repository.ProjektRepository;
import com.project.rest.utilities.MyUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Optional<Projekt> getProjekt(int projektId) {
        return projektRepository.findById(projektId);
    }

    @Override
    public List<Projekt> getProjekty()
    {
        return projektRepository.findAll();
    }

    @Override
    public void addProject(Projekt projekt)
    {
        projektRepository.save(projekt);
    }

    @Override
    public void deleteProject(Projekt projekt) {
        projektRepository.delete(projekt);
    }

    @Override
    public void updateProjekt(Projekt projekt) {
        projektRepository.updateProjekt(projekt.getNazwa(),projekt.getOpis(), MyUtilities.dateToString(projekt.getDataOddania()),projekt.getProjektId());
    }


}
