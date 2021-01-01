package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.repository.ProjektRepository;
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
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }

    public List<Projekt> getProjekty()
    {
        return projektRepository.findAll();
    }

    @PostConstruct
    public void testowyProjekt()
    {
        if(getProjekty().size()==0)
        {
            Projekt projekt=new Projekt();
            projekt.setOpis("Opis projektu");
            projekt.setNazwa("Projekt1");
            projekt.setDataOddania(LocalDate.of(2020,1,10));
            projekt.setDataczasUtworzenia(LocalDateTime.now());
            projektRepository.save(projekt);
            System.out.println("Projekt w bazie!!!");
        }
    }
}
