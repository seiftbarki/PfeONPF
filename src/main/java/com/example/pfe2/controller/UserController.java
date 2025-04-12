package com.example.pfe2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @GetMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        // Récupérer le nom d'utilisateur depuis l'authentification
        String username = authentication.getName();

        // Ajouter le nom d'utilisateur au modèle
        model.addAttribute("username", username);

        return "user/dashboard"; // Retourner le nom de la vue
    }
}
