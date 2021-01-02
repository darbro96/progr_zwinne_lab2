package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.model.Zadanie;
import com.project.rest.repository.ZadanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZadanieServiceImpl implements ZadanieService {
    private ZadanieRepository zadanieRepository;

    @Autowired
    public ZadanieServiceImpl(ZadanieRepository zadanieRepository) {
        this.zadanieRepository = zadanieRepository;
    }

    @Override
    public Optional<Zadanie> getZadanie(int zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public List<Zadanie> getZadania() {
        return zadanieRepository.findAll();
    }

    @Override
    public void addZadanie(Zadanie zadanie) {
        zadanieRepository.save(zadanie);
    }

    @Override
    public void usunZadanie(Zadanie zadanie) {
        zadanieRepository.delete(zadanie);
    }

    @Override
    public List<Zadanie> zadaniaZProjektu(Projekt projekt) {
        return zadanieRepository.zadaniaZProjektu(projekt.getProjektId());
    }


}
