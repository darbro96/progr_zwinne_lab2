package com.project.rest.controller;

import com.project.rest.model.Projekt;
import com.project.rest.model.Student;
import com.project.rest.model.Zadanie;
import com.project.rest.service.*;
import com.project.rest.utilities.MyUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

//    @Autowired
//    public ProjektRestController(ProjektService projektService) {
//        this.projektService = projektService;
//    }

    @GetMapping("/projekty")
    public List<Projekt> getProjekty() {
        return projektService.getProjekty();
    }

    @GetMapping("/projekt/{id}")
    public Optional<Projekt> getProjekt(@PathVariable("id") int idProjekt) {
        return projektService.getProjekt(idProjekt);
    }

    //UWAGA TO JEST REST DO USUNIĘCIA
    @GetMapping("/dodajZadanieTestowe/{idProjektu}")
    public void testowaMetoda(@PathVariable("idProjektu") int idProjekt) throws Exception {
        Projekt projekt = getProjekt(idProjekt).get();
        Zadanie zadanie = new Zadanie();
        zadanie.setDataczasOddania(LocalDateTime.of(2021, 01, 03, 22, 30));
        zadanie.setKolejnosc(1);
        zadanie.setNazwa("Zadanie " + projekt.getProjektId());
        zadanie.setOpis("Opis zadania " + projekt.getProjektId());
        zadanie.setProjekt(projekt);
        zadanieService.addZadanie(zadanie);
    }

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
        if (studentService.getStudent(idStudent).isPresent())
        {
            Student student=studentService.getStudent(idStudent).get();
            if (projektService.getProjekt(idProjektu).isPresent()) {
                Projekt projekt = projektService.getProjekt(idProjektu).get();
                projekt.getStudenci().add(student);
            }
        }
    }

    @DeleteMapping("/usunProjekt/{id}")
    public void usunProjekt(@PathVariable("id") int projektId)
    {
        Projekt projekt=projektService.getProjekt(projektId).get();
        projektService.deleteProject(projekt);
    }

    @GetMapping("/zadania")
    public List<Zadanie> getZadania() {
        return zadanieService.getZadania();
    }

    @GetMapping("/zadanie/{id}")
    public Optional<Zadanie> getZadanie(@PathVariable("id") int zadanieId) {
        return zadanieService.getZadanie(zadanieId);
    }

    @PostMapping("/addzadanie")
    public void addZadanie(@RequestParam("nazwa") String nazwa, @RequestParam("opis") String opis, @RequestParam("kolejnosc") int kolejnosc, @RequestParam("oddanie") String oddanie, @RequestParam("idProjektu") int projektId)
    {
        Projekt projekt=projektService.getProjekt(projektId).get();
        Zadanie zadanie=new Zadanie();
        zadanie.setProjekt(projekt);
        zadanie.setOpis(opis);
        zadanie.setNazwa(nazwa);
        zadanie.setKolejnosc(kolejnosc);
        zadanie.setDataczasOddania(MyUtilities.stringToDateTime(oddanie));
        zadanieService.addZadanie(zadanie);
    }

    @DeleteMapping("/usunzadanie/{id}")
    public void usunZadanie(@PathVariable("id") int idZadanie)
    {
        zadanieService.usunZadanie(zadanieService.getZadanie(idZadanie).get());
    }
}
