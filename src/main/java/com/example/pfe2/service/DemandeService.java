package com.example.pfe2.service;

import com.example.pfe2.entity.Demande;
import com.example.pfe2.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    // Trouver toutes les demandes
    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    // Trouver une demande par son ID
    public Demande findById(Long id) {
        return demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande non trouvée"));
    }

    // Sauvegarder ou mettre à jour une demande
    public Demande save(Demande demande) {
        return demandeRepository.save(demande);
    }
    // ✅ Récupérer les demandes assignées à un utilisateur (via son username)
    public List<Demande> findByUserUsername(String username) {
        return demandeRepository.findByUserUsername(username);
    }
}
