package com.example.pfe2.service;



import com.example.pfe2.entity.Role;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Enregistrer ou mettre à jour un utilisateur
    public void save(User user) {
        // Assigner le rôle ROLE_USER
        user.setRole(Role.ROLE_USER);

        // Crypter le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarder l'utilisateur
        userRepository.save(user);
    }

    // Trouver un DRH existant dans la base
    public User findDrh() {
        return userRepository.findByRole(Role.ROLE_ADMIN).orElse(null);
    }
}
