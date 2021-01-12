package com.project.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projekt") // potrzebne tylko jeżeli nazwa tabeli w bazie danych ma być inna od nazwy klasy
@Getter
@Setter
public class Projekt {
    @Id
    @GeneratedValue
    @Column(name = "projekt_id") // tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
    private Integer projektId;
    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String nazwa;
    @Column(name = "opis", length = 1000)
    private String opis;
    @CreationTimestamp
    @Column(name = "dataczas_utworzenia", nullable = false, updatable = false)
    private LocalDateTime dataczasUtworzenia;
    @Column(name = "data_oddania")
    private LocalDate dataOddania;
    @OneToMany(mappedBy = "projekt")
    @JsonIgnoreProperties("projekt") //bez tego był problem z generowaniem JSON
    private List<Zadanie> zadania;
    @ManyToMany
    @JoinTable(name = "projekt_student", joinColumns = {@JoinColumn(name = "projekt_id")}, inverseJoinColumns = {
            @JoinColumn(name = "student_id")})
    private Set<Student> studenci;

    public Projekt() {
    }
     public Projekt(Integer projektId, String nazwa, String opis, LocalDateTime dataczasUtworzenia, LocalDate dataOddania ) {
        this.projektId=projektId;
        this.nazwa=nazwa;
        this.opis=opis;
        this.dataczasUtworzenia=dataczasUtworzenia;
        this.dataOddania=dataOddania;
    }
}
