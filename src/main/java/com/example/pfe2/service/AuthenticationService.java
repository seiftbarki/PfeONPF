package com.example.pfe2.service;

import com.example.pfe2.dto.LoginUserDto;
import com.example.pfe2.dto.RegisterUserDto;
import com.example.pfe2.dto.VerifyUserDto;
import com.example.pfe2.entity.Role;
import com.example.pfe2.entity.User;
import com.example.pfe2.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Méthode pour inscrire un utilisateur
    public String signup(RegisterUserDto input) {
        // Créer un nouvel utilisateur avec un code de vérification
        User user = new User(
                input.getUsername(),
                input.getEmail(),
                passwordEncoder.encode(input.getPassword()),
                Role.ROLE_USER,
                false,
                generateVerificationCode(),
                LocalDateTime.now().plusMinutes(15),
                input.getDirection()
        );

        // Envoyer le code de vérification par email
        sendVerificationCode(user);

        // Sauver l'utilisateur dans la base de données
        userRepository.save(user);

        // Retourner la redirection vers la page de vérification
        return "redirect:/verify-code";
    }


    // Méthode pour authentifier un utilisateur
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Méthode pour vérifier un utilisateur après avoir saisi le code
    public boolean verifyUser(VerifyUserDto verifyUserDto) {
        Optional<User> user = userRepository.findByEmail(verifyUserDto.getEmail());
        if (user.isPresent() && user.get().getVerificationCode().equals(verifyUserDto.getVerificationCode())) {
            user.get().setEnabled(true);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }



    // Générer un code de vérification aléatoire
    public String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000));
    }

    // Méthode pour envoyer le code de vérification par email
    private void sendVerificationCode(User user) {
        String message = "Your verification code is: " + user.getVerificationCode();
        emailService.sendVerificationCode(user.getEmail(), "Account Verification", message);
    }

}
