package com.project.rest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "zadanie")
@Getter
@Setter
public class Zadanie {
    @Id
    @GeneratedValue
    @Column(name = "zadanie_id")
    private Integer zadanieId;
    @Column(name = "nazwa", nullable = false, length = 50)
    private String nazwa;
    @Column(name = "kolejnosc")
    private int kolejnosc;
    @Column(name = "opis", length = 1000)
    private String opis;
    @Column(name = "dataczas_oddania", nullable = false)
    private LocalDateTime dataczasOddania;
    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;
}
