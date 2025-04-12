package com.example.pfe2.controller;

import com.example.pfe2.entity.Direction;
import com.example.pfe2.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/directions")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    // Liste des directions
    @GetMapping
    public String listDirections(Model model) {
        List<Direction> directions = directionService.findAll();
        model.addAttribute("directions", directions);
        return "admin/directions-list"; // Le template Thymeleaf pour la liste
    }

    // Formulaire d'ajout
    @GetMapping("/add")
    public String addDirectionForm(Model model) {
        model.addAttribute("direction", new Direction());
        return "admin/add-direction"; // Le template Thymeleaf pour le formulaire d'ajout
    }

    // Enregistrement d'une nouvelle direction
    @PostMapping("/add")
    public String saveDirection(@ModelAttribute Direction direction) {
        directionService.save(direction);
        return "redirect:/admin/directions"; // Redirection vers la liste après ajout
    }

    // Formulaire de modification
    @GetMapping("/edit/{id}")
    public String editDirectionForm(@PathVariable Long id, Model model) {
        Direction direction = directionService.findById(id);
        model.addAttribute("direction", direction);
        return "admin/edit-direction"; // Le template Thymeleaf pour le formulaire de modification
    }

    // Mise à jour d'une direction
    @PostMapping("/edit/{id}")
    public String updateDirection(@PathVariable Long id, @ModelAttribute Direction direction) {
        direction.setId(id);
        directionService.save(direction);
        return "redirect:/admin/directions"; // Redirection vers la liste après mise à jour
    }

    // Suppression d'une direction
    @GetMapping("/delete/{id}")
    public String deleteDirection(@PathVariable Long id) {
        directionService.delete(id);
        return "redirect:/admin/directions"; // Redirection vers la liste après suppression
    }
}
