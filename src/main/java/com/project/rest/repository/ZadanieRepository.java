package com.project.rest.repository;

import com.project.rest.model.Zadanie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZadanieRepository extends JpaRepository<Zadanie, Integer> {
}
