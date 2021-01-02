package com.project.rest.repository;

import com.project.rest.model.Zadanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZadanieRepository extends JpaRepository<Zadanie, Integer> {
    @Query(value = "SELECT * FROM zadanie WHERE projekt_id = ?1", nativeQuery = true)
    List<Zadanie> zadaniaZProjektu(int projektId);
}
