package com.project.rest.service;

import com.project.rest.model.Projekt;

import java.util.List;
import java.util.Optional;

public interface ProjektService {
    Optional<Projekt> getProjekt(int projektId);
    List<Projekt> getProjekty();
    void addProject(Projekt projekt);
    void deleteProject(Projekt projekt);
    void updateProjekt(Projekt projekt);
}
