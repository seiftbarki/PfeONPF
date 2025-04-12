package com.example.pfe2.controller;

import com.example.pfe2.dto.LoginUserDto;
import com.example.pfe2.dto.RegisterUserDto;
import com.example.pfe2.dto.VerifyUserDto;
import com.example.pfe2.entity.Direction;
import com.example.pfe2.entity.User;
import com.example.pfe2.service.AuthenticationService;
import com.example.pfe2.service.DirectionService; // Assurez-vous d'importer le service des directions
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    private final AuthenticationService authenticationService;
    private final DirectionService directionService; // Service pour récupérer les directions

    public AuthController(AuthenticationService authenticationService, DirectionService directionService) {
        this.authenticationService = authenticationService;
        this.directionService = directionService; // Injection du service des directions
    }

    // Page de formulaire d'inscription
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new RegisterUserDto());

        // Récupérer les directions et les ajouter au modèle
        List<Direction> directions = directionService.findAll(); // Méthode pour récupérer toutes les directions
        model.addAttribute("directions", directions);

        return "register"; // Nom de la vue pour le formulaire d'inscription
    }

    // Gestion de l'inscription de l'utilisateur
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterUserDto userDto) {
        // Appel du service pour l'inscription et redirection vers la page de vérification
        return authenticationService.signup(userDto);
    }

    // Page pour entrer le code de vérification
    @GetMapping("/verify-code")
    public String verifyForm() {
        return "verify-code";
    }

    // Vérification du code saisi par l'utilisateur
    @PostMapping("/verify")
    public String verifyUser(@ModelAttribute VerifyUserDto verifyUserDto, Model model) {
        boolean verified = authenticationService.verifyUser(verifyUserDto);

        if (verified) {
            model.addAttribute("message", "Compte vérifié avec succès ! Connectez-vous.");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Code incorrect. Réessayez.");
            return "verify-code";
        }
    }





    // Page de connexion
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Gestion de la connexion de l'utilisateur
    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserDto loginRequestDto, Model model) {
        try {
            User user = authenticationService.authenticate(loginRequestDto);

            if (user != null) {
                String role = user.getRole().name();
                if ("ADMIN".equals(role)) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/user/dashboard";
                }
            } else {
                model.addAttribute("error", "Email ou mot de passe incorrect.");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erreur d'authentification : " + e.getMessage());
            return "login";
        }
    }

}
