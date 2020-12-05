package com.project.rest.repository;

import com.project.rest.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjektRepository extends JpaRepository<Projekt, Integer> {
}