package com.project.rest.controller;

import com.project.rest.bean.AuthenticationBean;
import com.project.rest.model.*;
import com.project.rest.service.ProjektServiceImpl;
import com.project.rest.service.StudentServiceImpl;
import com.project.rest.service.UserServiceImpl;
import com.project.rest.service.ZadanieServiceImpl;
import com.project.rest.utilities.MyUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProjektRestController {
    @Autowired
    private ProjektServiceImpl projektService;

    @Autowired
    private ZadanieServiceImpl zadanieService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private UserServiceImpl userService;

    //pobranie wszystkich projektów
    @GetMapping("/projekty")
    @Secured(Role.ROLE_ADMIN)
    public List<Projekt> getProjekty() {
        return projektService.getProjekty();
    }

    //pobranie wybranego projektu na podstawie id
    @GetMapping("/projekty/{id}")
    public Optional<Projekt> getProjekt(@PathVariable("id") int idProjekt) {
        return projektService.getProjekt(idProjekt);
    }

    //dodanie nowego projektu
    @PostMapping("/addProjekt")
    public void addProjekt(@RequestParam(value = "nazwa") String nazwa, @RequestParam(value = "opis") String opis, @RequestParam("oddanie") String oddanie) {
        Projekt projekt = new Projekt();
        projekt.setNazwa(nazwa);
        projekt.setOpis(opis);
        projekt.setDataOddania(MyUtilities.stringToDate(oddanie));
        projekt.setDataczasUtworzenia(LocalDateTime.now());
        projektService.addProject(projekt);
    }

    //dodawanie zadania do projektu
    @PostMapping("/zad-do-proj/{idZadania}/{idProjektu}")
    public void zadanieDoProjektu(@PathVariable("idZadania") int idZadania, @PathVariable("idProjektu") int idProjektu) {
        if (zadanieService.getZadanie(idZadania).isPresent()) {
            Zadanie zadanie = zadanieService.getZadanie(idZadania).get();
            if (projektService.getProjekt(idProjektu).isPresent()) {
                Projekt projekt = projektService.getProjekt(idProjektu).get();
                projekt.getZadania().add(zadanie);
            }
        }
    }

    //dodanie studenta do projektu
    @PostMapping("/stud-do-proj/{idStudenta}/{idProjektu}")
    public void studentDoProjektu(@PathVariable("idStudenta") int idStudent, @PathVariable("idProjektu") int idProjektu) {
        projektService.studentDoProjektu(projektService.getProjekt(idProjektu).get(), studentService.getStudent(idStudent).get());
    }

    //usunięcie projektu o danym id
    @DeleteMapping("/usunProjekt/{id}")
    public void usunProjekt(@PathVariable("id") int projektId) {
        Projekt projekt = projektService.getProjekt(projektId).get();
        projektService.deleteProject(projekt);
    }

    //pobranie wszystkich zadań
    @GetMapping("/zadania")
    public List<Zadanie> getZadania() {
        return zadanieService.getZadania();
    }

    //pobranie wybranego zadania na podstawie id
    @GetMapping("/zadanie/{id}")
    public Optional<Zadanie> getZadanie(@PathVariable("id") int zadanieId) {
        return zadanieService.getZadanie(zadanieId);
    }

    //dodanie nowego zadania
    @PostMapping("/addzadanie")
    public void addZadanie(@RequestParam("nazwa") String nazwa, @RequestParam("opis") String opis, @RequestParam("kolejnosc") int kolejnosc, @RequestParam("oddanie") String oddanie, @RequestParam("idProjektu") int projektId) {
        Projekt projekt = projektService.getProjekt(projektId).get();
        Zadanie zadanie = new Zadanie();
        zadanie.setProjekt(projekt);
        zadanie.setOpis(opis);
        zadanie.setNazwa(nazwa);
        zadanie.setKolejnosc(kolejnosc);
        zadanie.setDataczasOddania(MyUtilities.stringToDateTime(oddanie));
        zadanieService.addZadanie(zadanie);
    }

    //usunięcie zadania na podstawie id
    @DeleteMapping("/usunzadanie/{id}")
    public void usunZadanie(@PathVariable("id") int idZadanie) {
        zadanieService.usunZadanie(zadanieService.getZadanie(idZadanie).get());
    }

    //pobranie listy projeków powiązanych ze studentem
    @GetMapping("/projekty-student/{id}")
    public Set<Projekt> projektyStudenta(@PathVariable("id") int id) {
        return studentService.getStudent(id).get().getProjekty();
    }

    //pobranie listy zadań z danego projektu
    @GetMapping("/zadania-z-projektu/{id}")
    public List<Zadanie> zadaniaZProjektu(@PathVariable("id") int id) {
        Projekt projekt = projektService.getProjekt(id).get();
        return zadanieService.zadaniaZProjektu(projekt);
    }

    //wszyscy studenci
    @GetMapping("/studenci")
    @Secured(Role.ROLE_ADMIN) //dostęp tylko admin (prowadzący)
    public List<Student> studenci() {
        return studentService.getStudenci();
    }

    //rola aktualnie zalogowane użytkownika
    @GetMapping("/myrole")
    public String whoAmI() {
        return userService.roleOfUser(AuthenticationBean.getLoggedUser());
    }

    //id aktualnie zalogowanego użytkownika
    @GetMapping("/myiduser")
    public Long myIdUser() {
        return userService.idOfUser(AuthenticationBean.getLoggedUser());
    }

    //id studenta zalogowanego (null jeśli to nie student)
    @GetMapping("/myidstudent")
    @Secured(Role.ROLE_USER) //może wywołać tylko zwykły user
    public String myIdStudent() {
        try {
            return String.valueOf(userService.idOfStudent(AuthenticationBean.getLoggedUser()));
        } catch (NullPointerException ex) {
            return null;
        }
    }

    //dane wybranego studenta
    @GetMapping("/student/{id}")
    @Secured(Role.ROLE_ADMIN) //dostęp tylko admin
    public Student getStudent(@PathVariable("id") int id) {
        return studentService.getStudent(id).get();
    }

    //usunięcie studenta z projektu
    @DeleteMapping("usunZProjektu/{idProjektu}/{idStudenta}")
    @Secured(Role.ROLE_ADMIN) //dostęp tylko admin
    public void usunStudentaZProjektu(@PathVariable("idProjektu") int idProjektu, @PathVariable("idStudenta") int idStudenta) {
        projektService.usunStudentaZProjektu(projektService.getProjekt(idProjektu).get(), studentService.getStudent(idStudenta).get());
    }

    //nowy student
    @PostMapping("/nowyStudent")
    public void nowyStudent(@RequestParam("imie") String imie, @RequestParam("nazwisko") String nazwisko, @RequestParam("nr_indeksu") String index, @RequestParam("email") String email, @RequestParam("stacjonarny") boolean stacjonarny) {
        Student student=new Student();
        student.setImie(imie);
        student.setNazwisko(nazwisko);
        student.setNrIndeksu(index);
        student.setEmail(email);
        student.setStacjonarny(stacjonarny);
        studentService.addStudent(student);
    }

}

