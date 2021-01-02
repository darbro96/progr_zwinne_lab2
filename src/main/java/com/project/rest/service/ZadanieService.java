package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.model.Zadanie;

import java.util.List;
import java.util.Optional;

public interface ZadanieService {
    Optional<Zadanie> getZadanie(int zadanieId);
    List<Zadanie> getZadania();
    void addZadanie(Zadanie zadanie);
    void usunZadanie(Zadanie zadanie);
    List<Zadanie> zadaniaZProjektu(Projekt projekt);
}
