package com.project.rest.repository;

import com.project.rest.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjektRepository extends JpaRepository<Projekt, Integer> {
    @Modifying
    @Query("update Projekt p set p.nazwa = ?1, p.opis = ?2, p.dataOddania = ?3 where p.projektId = ?4")
    void updateProjekt(String nazwa, String opis, String dataOddania, int idProjektu);
}