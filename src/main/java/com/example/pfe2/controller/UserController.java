package com.example.pfe2.controller;

import com.example.pfe2.entity.Demande;
import com.example.pfe2.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @Autowired
    private DemandeService demandeService;

    @GetMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        String username = authentication.getName(); // Récupère l'utilisateur connecté
        List<Demande> demandes = demandeService.findByUserUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("demandes", demandes); // Ajoute les demandes au modèle
        return "user/dashboard";
    }
    @GetMapping("/rediger-acte/{id}")
    public String redigerActe(@PathVariable Long id, Model model) {
        Demande demande = demandeService.findById(id);
        model.addAttribute("demande", demande);
        return "user/rediger-acte"; // correspond à templates/user/rediger-acte.html
    }

}
