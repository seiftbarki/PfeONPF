package com.example.pfe2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Méthode pour envoyer le code de vérification avec un sujet et un message personnalisé
    public void sendVerificationCode(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);  // Sujet de l'email
        mailMessage.setText(message);     // Message de l'email
        mailSender.send(mailMessage);     // Envoi de l'email
    }

    // Méthode pour générer un code de vérification aléatoire à 6 chiffres
    public String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // Code à 6 chiffres
    }
}
