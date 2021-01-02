package com.project.rest.service;

import com.project.rest.model.*;
import com.project.rest.repository.RoleRepository;
import com.project.rest.repository.UserRepository;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

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
        if(userRepository.findAll().size()==0)
        {
            Role userRole =new Role(Role.ROLE_USER);
            Role adminRole  =new Role(Role.ROLE_ADMIN);
            roleRepository.save(userRole);
            roleRepository.save(adminRole);

            UserEntity user = new UserEntity();
            user.setUsername("admin");
            user.setPassword("$2y$12$lzmKyL7n336bSHBEh435/.FDe9R3NXFyHecQk970AUcgG1CyLG6Kq"); //hasło admin
            Set<Role> roles=new HashSet<>();
            roles.add(adminRole);
            user.setRoles(roles);
            userRepository.save(user);


            UserEntity user2 = new UserEntity();
            user2.setUsername("user");
            user2.setPassword("$2y$12$AlRASMMqJRlrdC8A6jR0v.pBIJaGC1wm1JHMtIK24aCWGooc3FVvC"); //hasło user
            Set<Role> roles2=new HashSet<>();
            roles2.add(userRole);
            user2.setRoles(roles2);
            userRepository.save(user2);
        }
    }
}
