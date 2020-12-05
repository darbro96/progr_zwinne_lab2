package com.project.rest.service;

import com.project.rest.model.Projekt;

import java.util.List;
import java.util.Optional;

public interface ProjektService {
    Optional<Projekt> getProjekt(Integer projektId);
    List<Projekt> getProjekty();
}
