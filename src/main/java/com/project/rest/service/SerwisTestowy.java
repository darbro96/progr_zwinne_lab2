package com.project.rest.service;

import com.project.rest.model.Projekt;
import com.project.rest.model.Student;
import com.project.rest.model.Zadanie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class SerwisTestowy {
    @Autowired
    private ProjektServiceImpl projektService;
    @Autowired
    private ZadanieServiceImpl zadanieService;
    @Autowired
    private StudentServiceImpl studentService;

    @PostConstruct
    private void generujDaneTestowe() {
        if (studentService.getStudenci().size() == 0) {
            System.out.println("DODAWANIE TESTOWEGO STUDENTA");
            Student student = new Student();
            student.setImie("Jan");
            student.setNazwisko("Kowalski");
            student.setNrIndeksu("100000");
            student.setEmail("student-jan@mail.com");
            student.setStacjonarny(true);
            studentService.addStudent(student);
        }
        if (projektService.getProjekty().size() == 0) {
            System.out.println("DODAWANIE TESTOWEGO PROJEKTU");
            Projekt projekt = new Projekt();
            projekt.setOpis("Opis projektu");
            projekt.setNazwa("Projekt1");
            projekt.setDataOddania(LocalDate.of(2020, 1, 10));
            projekt.setDataczasUtworzenia(LocalDateTime.now());
            Student student=studentService.getStudenci().get(0);
            Set<Student> students=new HashSet<>();
            students.add(student);
            projekt.setStudenci(students);
            projektService.addProject(projekt);
        }
        if (zadanieService.getZadania().size() == 0) {
            System.out.println("DODAWANIE TESTOWEGO ZADANIA");
            Projekt projekt = projektService.getProjekty().get(0);
            Zadanie zadanie = new Zadanie();
            zadanie.setDataczasOddania(LocalDateTime.now().plusDays(7));
            zadanie.setKolejnosc(1);
            zadanie.setNazwa("Zadanie " + projekt.getProjektId());
            zadanie.setOpis("Opis zadania " + projekt.getProjektId());
            zadanie.setProjekt(projekt);
            zadanieService.addZadanie(zadanie);
        }
    }
}
