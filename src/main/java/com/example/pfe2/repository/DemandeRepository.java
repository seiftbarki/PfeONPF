package com.example.pfe2.repository;

import com.example.pfe2.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByUserUsername(String username);
}
