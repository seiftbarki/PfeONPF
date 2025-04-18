package com.example.pfe2.service;

import com.example.pfe2.entity.Role;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final UserRepository userRepository;

    public EmployeeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    public User addEmployee(User employee) {
        // Assigner le rôle ROLE_USER
        employee.setRole(Role.ROLE_USER);

        // Crypter le mot de passe
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return userRepository.save(employee);
    }

    // Méthode pour mettre à jour un employé existant
    public User updateEmployee(User employee) {
        User existingUser = userRepository.findById(employee.getId()).orElseThrow(() ->
                new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + employee.getId()));

        // Vérifie si le mot de passe a été modifié (optionnel : à adapter selon ton frontend)
        if (!employee.getPassword().isEmpty() &&
                !passwordEncoder.matches(employee.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(employee.getPassword()));
        }

        // Met à jour les autres champs nécessaires
        existingUser.setUsername(employee.getUsername());
        existingUser.setEmail(employee.getEmail());
        existingUser.setDirection(employee.getDirection());

        return userRepository.save(existingUser);
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
