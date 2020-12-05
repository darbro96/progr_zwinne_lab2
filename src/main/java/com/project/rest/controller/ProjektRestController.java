package com.project.rest.controller;

import com.project.rest.model.Projekt;
import com.project.rest.service.ProjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjektRestController {
    private ProjektService projektService;

    @Autowired
    public ProjektRestController(ProjektService projektService) {
        this.projektService = projektService;
    }

    @GetMapping("/projekty")
    List<Projekt> getProjekty()
    {
        return projektService.getProjekty();
    }
}
