package com.example.pfe2.controller;

import com.example.pfe2.entity.Demande;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import com.example.pfe2.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listDemandes(Model model) {
        List<Demande> demandes = demandeService.findAll();
        List<User> users = userRepository.findAll();
        model.addAttribute("demandes", demandes);
        model.addAttribute("users", users);
        return "admin/demandes-list";
    }

    @PostMapping("/assign")
    public String assignUser(@RequestParam Long demandeId, @RequestParam Long userId) {
        Demande demande = demandeService.findById(demandeId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        demande.setUser(user);
        demandeService.save(demande);
        return "redirect:/admin/demandes";
    }
}
