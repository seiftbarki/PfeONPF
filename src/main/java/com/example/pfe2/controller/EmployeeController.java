package com.example.pfe2.controller;

import com.example.pfe2.entity.Direction;
import com.example.pfe2.entity.User;
import com.example.pfe2.service.DirectionService;
import com.example.pfe2.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DirectionService directionService;

    // Constructeur avec injection de dépendances
    public EmployeeController(EmployeeService employeeService, DirectionService directionService) {
        this.employeeService = employeeService;
        this.directionService = directionService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<User> users = employeeService.getAllUsersExceptAdmin(); // Récupère tous les utilisateurs
        model.addAttribute("employees", users);
        return "admin/employees-list"; // Renvoie la vue pour la liste des utilisateurs
    }

    // Méthode pour activer un employé
    @PostMapping("/enable/{id}")
    public String enableEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.updateEmployeeStatus(id, true); // Activer l'employé
            redirectAttributes.addFlashAttribute("success", "L'employé a été activé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'activation de l'employé.");
        }
        return "redirect:/admin/employees/list";
    }

    // Méthode pour désactiver un employé
    @PostMapping("/disable/{id}")
    public String disableEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.updateEmployeeStatus(id, false); // Désactiver l'employé
            redirectAttributes.addFlashAttribute("success", "L'employé a été désactivé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la désactivation de l'employé.");
        }
        return "redirect:/admin/employees/list";
    }

    // Méthode pour afficher le formulaire d'ajout d'un employé
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new User()); // Ajouter un objet User vide pour le formulaire
        List<Direction> directions = directionService.findAll(); // Récupérer les directions depuis la base de données
        model.addAttribute("directions", directions); // Ajouter les directions au modèle
        return "admin/add-employee"; // Renvoie la vue pour ajouter un nouvel employé
    }

    // Méthode pour traiter le formulaire d'ajout d'un employé
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") User employee) {
        employeeService.addEmployee(employee); // Ajoute l'employé
        return "redirect:/admin/employees/list"; // Redirige vers la liste des employés
    }

    // Méthode pour supprimer un employé
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id); // Méthode qui supprime l'employé par ID
            redirectAttributes.addFlashAttribute("success", "L'employé a été supprimé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'employé.");
        }
        return "redirect:/admin/employees/list"; // Redirige vers la liste des employés après la suppression
    }
}
