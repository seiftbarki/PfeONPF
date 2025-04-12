package com.example.pfe2.service;

import com.example.pfe2.entity.Role;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final UserRepository userRepository;

    public EmployeeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Méthode pour obtenir tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Récupère tous les utilisateurs
    }
    public List<User> getAllUsersExceptAdmin() {
        return userRepository.findByUsernameNot("admin");
    }


    public User getEmployeeById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Employé non trouvé"));
    }

    // Méthode pour ajouter un nouvel employé
    public User addEmployee(User employee) {
        return userRepository.save(employee);
    }

    // Méthode pour mettre à jour un employé existant
    public User updateEmployee(User employee) {
        return userRepository.save(employee);
    }

    // Méthode pour supprimer un employé par son ID
    public void deleteEmployee(Long id) {
        userRepository.deleteById(id);
    }
    // Méthode pour activer ou désactiver un employé
    @Transactional
    public void updateEmployeeStatus(Long id, boolean enabled) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        user.setEnabled(enabled); // Mise à jour de l'attribut 'enabled'
        userRepository.save(user);
    }

}
