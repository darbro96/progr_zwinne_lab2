package com.project.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "imie", nullable = false, length = 50)
    private String imie;
    @Column(name = "nazwisko", nullable = false, length = 100)
    private String nazwisko;
    @Column(name = "nr_indeksu", nullable = false, unique = true, length = 20)
    private String nrIndeksu;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "stacjonarny", nullable = false)
    private boolean stacjonarny;
    @ManyToMany(mappedBy = "studenci")
    @JsonIgnoreProperties("studenci") //bez tego był problem z generowaniem JSON
    private Set<Projekt> projekty;

    public Student() {
    }
}
