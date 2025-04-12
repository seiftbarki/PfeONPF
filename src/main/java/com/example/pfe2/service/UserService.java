package com.example.pfe2.service;



import com.example.pfe2.entity.Role;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Enregistrer ou mettre à jour un utilisateur
    public void save(User user) {
        userRepository.save(user);
    }

    // Trouver un DRH existant dans la base
    public User findDrh() {
        return userRepository.findByRole(Role.ROLE_ADMIN).orElse(null);
    }
}
